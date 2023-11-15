package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    private static final String url = "jdbc:mysql://localhost:3306/Agencia_Viagens";
    private static final String user = "root";
    private static final String password = "Edn@2003";

    private static Connection conn;

    /* metodo para conexão com o banco de dados */

    public static Connection getConexao() {
        try {
            /* criando uma conexão caso ela não tenha sido criada ainda */
            if (conn == null) {
                conn = DriverManager.getConnection(url, user, password);
                return conn;
            }
            /* se já tiver sido criada apenas retorne ela */
            else
            {
                return conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
