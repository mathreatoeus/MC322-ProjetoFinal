package controllers;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import models.usuario.Cliente;

public class UsuarioControllerImpl {

    /* metodo para cadastrar usuário */

    public void cadastrarUsuario(Cliente usuario) {

        /* Verificar se já existe um usuário com mesmo CPF ou e-mail */
        if (usuarioExiste(usuario.getCpf(), usuario.getEmail())) {
            /* lançar uma mensagem no Front-end --> COMO FAZ ISSO??? */
            return;
        }

        /* Inserir novo usuário no banco de dados */
        /* Antes precisa verificar se é um funcionário ou cliente */
        String sql = "INSERT INTO CLIENTE (NOME, NASCIMENTO, RG, CPF, EMAIL, SENHA, CELULAR, TELEFONERESIDENCIAL, ENDERECO, NUMEROCARTAO, VALIDADE, CVV, NOMECARTAO, DATAREGISTRO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, usuario.getNome());

            // Converter o LocalDate para java.sql.Date
            Date dataNascimento = Date.valueOf(usuario.getNascimento());
            ps.setDate(2, dataNascimento);
            ps.setString(3, usuario.getRg());
            ps.setString(4, usuario.getCpf());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getSenha());
            ps.setString(7, usuario.getCelular());
            ps.setString(8, usuario.getTelefoneResidencial());
            ps.setString(9, usuario.getEndereco());
            ps.setString(10, usuario.getNumeroCartao());

            // Converter o LocalDate para java.sql.Date
            Date validadeCartao = Date.valueOf(usuario.getValidade());
            ps.setDate(11, validadeCartao);
            ps.setInt(12, usuario.getCvv());
            ps.setString(13, usuario.getNomeCartao());

            // Converter o LocalDate para java.sql.Date
            Date dataRegistro = Date.valueOf(usuario.getDataRegistro());
            ps.setDate(14, dataRegistro);

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Metodo para verificar se já existe um usuário com mesmo CPF ou e-mail */
    private boolean usuarioExiste(String cpf, String email) {
        try {
            String sql = "SELECT COUNT(*) FROM CLIENTE WHERE CPF = ? OR EMAIL = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, cpf);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe um registro com o CPF ou email
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * verificação de credenciais do usuário - retorno deve ser usado pelo Front-end
     */
    private boolean credenciais(String email, String senha) {
        try {
            String sql = "SELECT COUNT(*) FROM USUARIO WHERE EMAIL = ? OR SENHA = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, as credenciais são válidas
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
