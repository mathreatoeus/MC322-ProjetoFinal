package controllers;

import java.util.ArrayList;
import java.util.List;
import models.pacote.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PacoteControllerImpl {
   List<Pacote> ListaPacotes = new ArrayList<>(); //necessário?? faltam coisas no banco

    public void cadastrarPacote(Pacote pacote){
        /* Verificar se já existe um pacote igual */
        if (PacoteExiste(pacote.getId())) {
            /* lançar uma mensagem no Front-end --> COMO FAZ ISSO??? */
            return;
        }
        String sql = "INSERT INTO Pacote (DESTINO, HOSPEDAGEM, TIPOPASSAGEM, PASSAGEM, ALUGUELCARRO, PRECOTOTAL, AVALIACAO) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

       /* try { como fazercom a classe de pacotes atual? tem que deixar igual as outras? 
            ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString
            ps.setString
          

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }


    public void editarPacote(Pacote pacote){
        //implementar lógica
    }

    public void deletarPacote(Pacote pacote){
        //implementar lógica
    }

    public boolean PacoteExiste(String id){
        try {
            String sql = "SELECT COUNT(*) FROM Pacote WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /*verificando se há algum retorno na busca no banco de dados*/
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe um pacote com mesmo id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
