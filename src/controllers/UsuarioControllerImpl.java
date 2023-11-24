package controllers;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.usuario.Cliente;
import models.usuario.Funcionario;

public class UsuarioControllerImpl {

    /* metodo para cadastrar usuário tipo cliente */

    public void cadastrarCliente(Cliente usuario) {

        /* Verificar se já existe um usuário com mesmo CPF ou e-mail */
        if (clienteExiste(usuario.getCpf(), usuario.getEmail())) {
            /* lançar uma mensagem no Front-end --> COMO FAZ ISSO??? */
            return;
        }
        String sql = "INSERT INTO Cliente (NOME, NASCIMENTO, CPF, EMAIL, SENHA, CELULAR, ENDERECO, NUMEROCARTAO, VALIDADE, CVV, NOMECARTAO, DATAREGISTRO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, usuario.getNome());

            // Converter o LocalDate para java.sql.Date
            Date dataNascimento = Date.valueOf(usuario.getNascimento());
            ps.setDate(2, dataNascimento);
            ps.setString(3, usuario.getCpf());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getSenha());
            ps.setString(6, usuario.getCelular());
            ps.setString(7, usuario.getEndereco());
            ps.setString(8, usuario.getNumeroCartao());

            // Converter o LocalDate para java.sql.Date
            Date validadeCartao = Date.valueOf(usuario.getValidade());
            ps.setDate(9, validadeCartao);
            ps.setInt(10, usuario.getCvv());
            ps.setString(11, usuario.getNomeCartao());

            // Converter o LocalDate para java.sql.Date
            Date dataRegistro = Date.valueOf(usuario.getDataRegistro());
            ps.setDate(12, dataRegistro);

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* metodo para cadastrar usuário tipo funcionario */

    public void cadastrarFuncionario(Funcionario usuario) {

        /* Verificar se já existe um usuário com mesmo CPF ou e-mail */
        if (funcionarioExiste(usuario.getCpf(), usuario.getEmail())) {
            /* lançar uma mensagem no Front-end --> COMO FAZ ISSO??? */
            return;
        }
        String sql = "INSERT INTO Funcionario (NOME, NASCIMENTO, CPF, EMAIL, SENHA, CELULAR, ENDERECO, CARGO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, usuario.getNome());

            // Converter o LocalDate para java.sql.Date
            Date dataNascimento = Date.valueOf(usuario.getNascimento());
            ps.setDate(2, dataNascimento);
            ps.setString(3, usuario.getCpf());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getSenha());
            ps.setString(6, usuario.getCelular());
            ps.setString(7, usuario.getEndereco());
            ps.setString(8, usuario.getCargo().name());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Metodo para verificar se já existe um usuário tipo cliente com mesmo CPF ou
     * e-mail
     */
    private boolean clienteExiste(String cpf, String email) {
        try {
            String sql = "SELECT COUNT(*) FROM Cliente WHERE CPF = ? OR EMAIL = ?";
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
     * Metodo para verificar se já existe um usuário tipo funcionario com mesmo CPF
     * ou e-mail
     */
    private boolean funcionarioExiste(String cpf, String email) {
        try {
            String sql = "SELECT COUNT(*) FROM Funcionario WHERE CPF = ? OR EMAIL = ?";
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
     * verificação de credenciais do cliente - retorno deve ser usado pelo Front-end
     */
    public boolean credenciaisCliente(String email, String senha) {
        try {
            String sql = "SELECT COUNT(*) FROM Cliente WHERE EMAIL = ? OR SENHA = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Credenciais validas -> usar no front");
                return count > 0; // Se count for maior que 0, as credenciais são válidas
            } else {
                System.out.println("Credenciais invalidas -> usar no front");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * verificação de credenciais do funcionario - retorno deve ser usado pelo Front-end
     */
    public boolean credenciaisFuncionario(String email, String senha) {
        try {
            String sql = "SELECT COUNT(*) FROM Funcionario WHERE EMAIL = ? OR SENHA = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Credenciais validas -> usar no front");
                return count > 0; // Se count for maior que 0, as credenciais são válidas
            } else {
                System.out.println("Credenciais invalidas -> usar no front");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
