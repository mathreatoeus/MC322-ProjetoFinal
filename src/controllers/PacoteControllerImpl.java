package controllers;

import java.util.ArrayList;
import java.util.List;
import models.pacote.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        String sql = "INSERT INTO Pacote (destino, hospedagem, tipoPassagem, passagem, aluguelCarro, desconto, preco, media_avaliacoes, num_avaliacoes, fechado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        

        try (PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql)) {
            ps.setInt(1, pacote.getIdDestino());
            ps.setInt(2, pacote.getIdHospedagem());
            ps.setString(3, pacote.getTipoPassagem().name()); 
            ps.setInt(4, pacote.getIdPassagem());
            ps.setInt(5, pacote.getIdAluguelCarro());
            ps.setDouble(6, pacote.getDesconto());
            ps.setDouble(7, pacote.getPreco());
            ps.setDouble(8, pacote.getMediaAvaliacoes());
            ps.setInt(9, pacote.getNumAvaliacoes());
            ps.setBoolean(10, pacote.getFechado());
    
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarAluguelCarro(AluguelCarro aluguel) {
        /* Verificar se já existe um aluguel com o mesmo ID */
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
            ps.setInt(10, aluguel.getIdSeguro());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarAtividade(Atividade atividade, Pacote pacote) {

        if (AtividadeExiste(atividade.getId())) {

            return;
        }
        String sqlInserirAtividade = "INSERT INTO Atividade (nome_atividade, descricao, localizacao, endereco, inicio, fim, preco) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sqlInserirAtividade, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, atividade.getNomeAtividade());
            ps.setString(2, atividade.getDescricao());
            ps.setInt(3, atividade.getLocal());
            ps.setString(4, atividade.getEndereco());
    
            // Convertendo LocalDateTime para Timestamp
            LocalDateTime inicioLocalDateTime = atividade.getInicio();
            Timestamp inicioTimestamp = Timestamp.valueOf(inicioLocalDateTime);
            ps.setTimestamp(5, inicioTimestamp);
    
            LocalDateTime fimLocalDateTime = atividade.getFim();
            Timestamp fimTimestamp = Timestamp.valueOf(fimLocalDateTime);
            ps.setTimestamp(6, fimTimestamp);
    
            ps.setDouble(7, atividade.getPreco());
    
            ps.executeUpdate();
    
            ResultSet generatedKeys = ps.getGeneratedKeys();
            int idAtividadeInserida = -1;
            if (generatedKeys.next()) {
                idAtividadeInserida = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID da atividade inserida.");
            }
    
            String sqlInserirAtividadePacote = "INSERT INTO ativadesPacote (pacote, atividade) VALUES (?, ?)";
            try (PreparedStatement psAtividadePacote = ConexaoMySQL.getConexao().prepareStatement(sqlInserirAtividadePacote)) {
                psAtividadePacote.setInt(1, pacote.getId());
                psAtividadePacote.setInt(2, idAtividadeInserida);
    
                psAtividadePacote.executeUpdate();
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarComentarioHospedagem(Comentario comentario, int idCliente, int idHospedagem) {

        if (ComentariosHospedagensExiste(comentario.getId())) {

            return;
        }
        try {
            String sql = "INSERT INTO ComentariosHospedagens (cliente, data_e_hora_da_postagem, comentario, hospedagem) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, idCliente);
            ps.setTimestamp(2, Timestamp.valueOf(comentario.getDataEHoraDaPostagem()));
            ps.setString(3, comentario.getComentario());
            ps.setInt(4, idHospedagem);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public void cadastrarComentarioLocalizacoes(Comentario comentario, int idCliente, int idDestino) {

         if (ComentariosLocalizacoesExiste(comentario.getId())) {

            return;
        }
        try {
            String sql = "INSERT INTO ComentariosLocalizacoes (cliente, data_e_hora_da_postagem, comentario, localizacao) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, idCliente);
            ps.setTimestamp(2, Timestamp.valueOf(comentario.getDataEHoraDaPostagem()));
            ps.setString(3, comentario.getComentario());
            ps.setInt(4, idDestino);
    
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarComentarioPacote(Comentario comentario, int idCliente, int idPacote) {

         if (ComentariosPacotesExiste(comentario.getId())) {

            return;
        }
        try {
            String sql = "INSERT INTO ComentariosPacotes (cliente, data_e_hora_da_postagem, comentario, pacote) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, idCliente);
            ps.setTimestamp(2, Timestamp.valueOf(comentario.getDataEHoraDaPostagem()));
            ps.setString(3, comentario.getComentario());
            ps.setInt(4, idPacote);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public void cadastrarHospedagem(Hospedagem hospedagem) {

         if (HospedagemExiste(hospedagem.getIdHospedagem())) {

            return;
        }
         try {
            String sql = "INSERT INTO Hospedagem (nome, tipo_hospedagem, tipo_suite, tipo_cama, descricao, endereco, localizacao, "
                    + "checkin, checkout, diaria, num_diarias, preco, mediaAvaliacoes, numAvaliacoes, disponivel) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, hospedagem.getNome());
            ps.setString(2, hospedagem.getTipoHospedagem().name());
            ps.setString(3, hospedagem.getTipoSuite());
            ps.setString(4, hospedagem.getTipoCama().name());
            ps.setString(5, hospedagem.getDescricao());
            ps.setString(6, hospedagem.getEndereco());
            ps.setInt(7, hospedagem.getIdLocal());
            ps.setTimestamp(8, Timestamp.valueOf(hospedagem.getCheckin()));
            ps.setTimestamp(9, Timestamp.valueOf(hospedagem.getCheckout()));
            ps.setDouble(10, hospedagem.getDiaria());
            ps.setInt(11, hospedagem.getNumDiarias());
            ps.setDouble(12, hospedagem.getPreco());
            ps.setDouble(13, hospedagem.getAvaliacao());
            ps.setInt(14, hospedagem.getNumAvaliacoes());
            ps.setBoolean(15, hospedagem.getDisponivel());

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cadastrarLocal(Local local) {

         if (LocalizacaoExiste(local.getIdLocal())) {

            return;
        }
         try {
              String sql = "INSERT INTO Localizacao (nome, continente, mediaAvaliacoes, numAvaliacoes) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, local.getNome());
            ps.setString(2, local.getContinente().name());
            ps.setDouble(3, local.getMediaAvaliacoes());
            ps.setInt(4, local.getNumAvaliacoes());

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public void cadastrarPagamento(Pagamento pagamento) {

         if (PagamentoExiste(pagamento.getIdPagamento())) {

            return;
        }
         try {
            String sql = "INSERT INTO Pagamento (cliente, valor, situacao, vencimento) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pagamento.getIdUsuario());
            ps.setDouble(2, pagamento.getValor());
            ps.setString(3, pagamento.getSituacao().name());
            ps.setDate(4, Date.valueOf(pagamento.getVencimento()));
            ps.executeUpdate();

            ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            
    }

    public void cadastrarPassagem(Passagem passagem) {
        try {
          

            // Verificar o tipo da passagem
            if (passagem instanceof PassagemAerea) {
                if (PassagemAereaExiste(passagem.getId())) {

                    return;
                }else{
                    adicionarPassagemAerea((PassagemAerea) passagem);
                }
                
            } else if (passagem instanceof PassagemOnibus) {
                if (PassagemOnibusExiste(passagem.getId())) {

                    return;
                }else{
                    adicionarPassagemOnibus((PassagemOnibus) passagem);
                }
                
            }

             
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private void adicionarPassagemAerea(PassagemAerea passagemAerea) throws SQLException {
        String sql = "INSERT INTO PassagemAerea (localPartida, localChegada, saida, chegada, duracao, companhia, preco, aeroporto_partida, aeroporto_chegada, iataPartida, iataDestino) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, passagemAerea.getIdPartida());
        ps.setInt(2, passagemAerea.getIdDestino());
        ps.setTimestamp(3, Timestamp.valueOf(passagemAerea.getSaida()));
        ps.setTimestamp(4, Timestamp.valueOf(passagemAerea.getChegada()));
        ps.setDouble(5, passagemAerea.getDuracao());
        ps.setString(6, passagemAerea.getCompanhia());
        ps.setDouble(7, passagemAerea.getPreco());
        ps.setString(8, passagemAerea.getAeroportoPartida());
        ps.setString(9, passagemAerea.getAeroportoChegada());
        ps.setString(10, passagemAerea.getIataPartida());
        ps.setString(11, passagemAerea.getIataChegada());

        ps.executeUpdate();
        ps.close();
    }

    private void adicionarPassagemOnibus(PassagemOnibus passagemOnibus) throws SQLException {
        String sql = "INSERT INTO PassagemOnibus (localPartida, localChegada, saida, chegada, duracao, companhia, preco, enderecoPartida, enderecoChegada) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, passagemOnibus.getIdPartida());
        ps.setInt(2, passagemOnibus.getIdDestino());
        ps.setTimestamp(3, Timestamp.valueOf(passagemOnibus.getSaida()));
        ps.setTimestamp(4, Timestamp.valueOf(passagemOnibus.getChegada()));
        ps.setObject(5, passagemOnibus.getDuracao()); 
        ps.setString(6, passagemOnibus.getCompanhia());
        ps.setDouble(7, passagemOnibus.getPreco());
        ps.setString(8, passagemOnibus.getEnderecoPartida());
        ps.setString(9, passagemOnibus.getEnderecoChegada());

        ps.executeUpdate();
        ps.close();
    }

    
     public void cadastrarReserva(Reserva reserva) {

         if (reservaExiste(reserva.getId())) {

            return;
        }
         try {
            String sql = "INSERT INTO Reserva (pacote, usuario, entrada, saida, pagamento, desconto, preco) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reserva.getIdPacote());
            ps.setInt(2, reserva.getIdUsuario());
            ps.setTimestamp(3, Timestamp.valueOf(reserva.getEntrada()));
            ps.setTimestamp(4, Timestamp.valueOf(reserva.getSaida()));
            ps.setInt(5, reserva.getIdPagamento());
            ps.setDouble(6, reserva.getDesconto());
            ps.setDouble(7, reserva.getPreco());
            ps.executeUpdate();

            ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            
    }

    public void cadastrarSeguro(Seguro seguro) {

         if (SeguroExiste(seguro.getId())) {

            return;
        }
         try {
            String sql = "INSERT INTO Seguro (franquia, descricao) VALUES (?, ?)";

            PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, seguro.getFranquia());
            ps.setString(2, seguro.getDescricao());

            ps.executeUpdate();

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

    public boolean ComentariosPacotesExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM ComentariosPacotes  WHERE ID = ?";
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

    public boolean ComentariosHospedagensExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM ComentariosHospedagens WHERE ID = ?";
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

    public boolean ComentariosLocalizacoesExiste(int id) {
        try {
            String sql = "SELECT COUNT(*) FROM ComentariosLocalizacoes  WHERE ID = ?";
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
        String selectSql = "SELECT COUNT(*) FROM ComentariosHospedagens WHERE ID = ?";
        String deleteSql = "DELETE FROM ComentariosHospedagens WHERE ID = ?";

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
        String selectSql = "SELECT COUNT(*) FROM ComentariosLocalizacoes WHERE ID = ?";
        String deleteSql = "DELETE FROM ComentariosLocalizacoes WHERE ID = ?";

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

    public void excluirComentarios_Pacote(int id) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM ComentariosPacotes WHERE ID = ?";
        String deleteSql = "DELETE FROM ComentariosPacotes WHERE ID = ?";

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

    //busca de um item em um pacote

    //envio de email ao fazer reserva
    

}
