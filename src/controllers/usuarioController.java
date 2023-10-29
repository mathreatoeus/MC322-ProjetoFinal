package controllers;
import models.usuario.Cliente;
import models.usuario.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class usuarioController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Agencia_Viagens";

    /* Cadastrar um novo usuário */
    /* Receber informações do usuário pelo Front-end */
    Usuario usuario = new Cliente(0, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);

        // Método para verificar se um usuário já existe no banco de dados
        public boolean usuarioExiste(Usuario usuario) {
            try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
                String sql = "SELECT COUNT(*) FROM Cliente WHERE cpf = ? OR email = ? OR senha = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, usuario.getCpf());
                    preparedStatement.setString(2, usuario.getEmail());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int count = resultSet.getInt(1);
                            return count > 0;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Lide com exceções de banco de dados aqui
            }
            return false;
        }
    


}
