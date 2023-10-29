package controllers;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.usuario.Usuario;
import controllers.ConexaoMySQL;

public class UsuarioControllerImpl {

    /* metodo para cadastrar usuário */

    public void cadastrarUsuario(Usuario usuario) {

        /*
         * antes precisa verificar se o CPF ou email do usuario já não está no banco de
         * dados
         */

        String sql = "INSERT INTO USUARIO (NOME, NASCIMENTO, RG, CPF, EMAIL, SENHA, CELULAR) VALUES (?, ?, ?, ?, ?, ?, ?)";

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
            ps.setString(7, usuario.getSenha());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
