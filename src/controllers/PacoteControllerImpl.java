package controllers;

import java.util.ArrayList;
import java.util.List;
import models.pacote.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PacoteControllerImpl {

    // cadastro no banco de dados

    public void cadastrarPacote(Pacote pacote) {
        /* Verificar se já existe um pacote igual */
        if (PacoteExiste(pacote.getId())) {

            return;
        }
        String sql = "INSERT INTO Pacote (DESTINO, HOSPEDAGEM, TIPOPASSAGEM, PASSAGEM, ALUGUELCARRO, PRECOTOTAL, AVALIACAO) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try { // de onde pegar essas informações? lista ocupa meória, classe pacote com
              // atributos de ID dos itens, muts agurmentos na construção(problema null)
            ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setString(1, "teste");
            ps.setString(2, "teste");
            ps.setString(3, "teste");
            ps.setString(4, "teste");
            ps.setString(5, "teste");
            ps.setString(6, "teste");
            ps.setString(7, "teste");

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarAluguelCarro(AluguelCarro aluguel) {
        /* Verificar se já existe um pacote igual */
        if (reservaExiste(aluguel.getId())) {

            return;
        }
        String sql = "INSERT INTO AluguelCarro (NUM_DIARIAS, MODELO, EMPRESA, RETIRADA, DEVOLUCAO, ENDERECO_RETIRADA, ENDERECO_DEVOLUCAO, DIARIA, PRECO, SEGURO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, aluguel.getNumDiarias());
            ps.setString(2, aluguel.getModeloCarro());
            ps.setString(3, aluguel.getLocadora());

            // Convertendo LocalDateTime para Timestamp
            LocalDateTime retiradaLocalDateTime = aluguel.getRetirada();
            Timestamp retiradaTimestamp = Timestamp.valueOf(retiradaLocalDateTime);
            ps.setTimestamp(4, retiradaTimestamp);
            LocalDateTime devolucaoLocalDateTime = aluguel.getDevolucao();
            Timestamp devolucaoTimestamp = Timestamp.valueOf(devolucaoLocalDateTime);
            ps.setTimestamp(5, devolucaoTimestamp);
            ps.setString(6, aluguel.getEnderecoRetirada());
            ps.setString(7, aluguel.getEnderecoDevolucao());
            ps.setDouble(8, aluguel.getDiaria());
            ps.setDouble(9, aluguel.getPreco());
            ps.setString(10, aluguel.getSeguro());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // verificação de existência


    public boolean PacoteExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM Pacote WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean AluguelCarroExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM AluguelCarro WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean AtividadeExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM Atividade WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean PagamentoExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM Pagamento WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean LocalizacaoExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM Localizacao WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean HospedagemExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM Hospedagem WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe um pacote com mesmo id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean SeguroExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM Seguro WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean PassagemAereaExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM PassagemAerea WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean PassagemOnibusExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM PassagemOnibus WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean reservaExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM reserva  WHERE ID = ?";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); // executando uma consulta no banco de dados

            /* verificando se há algum retorno na busca no banco de dados */
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // exclusão

    public void excluirAluguelCarro(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM AluguelCarro WHERE ID = ?";
        String deleteSql = "DELETE FROM AluguelCarro WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirAtividade(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM atividadesPacote WHERE ID = ?";
        String deleteSql = "DELETE FROM atividadesPacote WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirPagamento(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Pagamento WHERE ID = ?";
        String deleteSql = "DELETE FROM Pagamento WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirLocalizacao(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Localizacao WHERE ID = ?";
        String deleteSql = "DELETE FROM Localizacao WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirHospedagem(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Hospedagem WHERE ID = ?";
        String deleteSql = "DELETE FROM Hospedagem WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirComentarios_Hospedagens(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Comentarios_Hospedagens WHERE ID = ?";
        String deleteSql = "DELETE FROM Comentarios_Hospedagem WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirComentarios_Localizacoes(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Comentarios_Localizacoes WHERE ID = ?";
        String deleteSql = "DELETE FROM Comentarios_Localizacoes WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirSeguro(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Seguro WHERE ID = ?";
        String deleteSql = "DELETE FROM Seguro WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirPassagemAerea(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM PassagemAerea WHERE ID = ?";
        String deleteSql = "DELETE FROM PassagemAerea WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirPassagemOnibus(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM PassagemOnibus WHERE ID = ?";
        String deleteSql = "DELETE FROM PassagemOnibus WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirPacote(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Pacote WHERE ID = ?";
        String deleteSql = "DELETE FROM Pacote WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

    public void excluirreserva(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM reserva WHERE ID = ?";
        String deleteSql = "DELETE FROM reserva WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Item encontrado, agora vamos excluir
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setInt(1, id);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException("Falha ao excluir o item com ID " + id + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("Item com ID " + id + " não encontrado.");
            }
        }
    }

}
