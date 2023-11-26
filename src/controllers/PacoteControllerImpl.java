package controllers;

import java.util.ArrayList;
import java.util.List;
import models.pacote.*;
import models.pacote.Hospedagem.TipoCama;
import models.pacote.Hospedagem.TipoHospedagem;
import models.pacote.Hospedagem.TipoSuite;
import models.pacote.Pacote.Categoria;
import models.pacote.Pacote.TipoPassagem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PacoteControllerImpl {

    private  static ArrayList<Pacote> listapacotes;

    // cadastro no banco de dados

    public void cadastrarPacote(Pacote pacote) {
        /* Verificar se já existe um pacote igual */
        if (PacoteExiste(pacote.getId())) {

            return;
        }
        pacote.setPreco(somarpagamento(pacote.getId()));
        listapacotes.add(pacote);
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

        try (PreparedStatement ps = ConexaoMySQL.getConexao().prepareStatement(sqlInserirAtividade,
                Statement.RETURN_GENERATED_KEYS)) {
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
            try (PreparedStatement psAtividadePacote = ConexaoMySQL.getConexao()
                    .prepareStatement(sqlInserirAtividadePacote)) {
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
        if(pagamento.getVencimento().isBefore(LocalDate.now() )&& pagamento.getSituacao()!= Pagamento.Situacao.PAGO){
            pagamento.setSituacao(Pagamento.Situacao.ATRASADO);
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
                } else {
                    adicionarPassagemAerea((PassagemAerea) passagem);
                }

            } else if (passagem instanceof PassagemOnibus) {
                if (PassagemOnibusExiste(passagem.getId())) {

                    return;
                } else {
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
                // Item encontrado
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
        for (int i = 0; i < listapacotes.size(); i++) {
            Pacote pacote = listapacotes.get(i);

            // Verifica se o ID do pacote atual é igual ao ID fornecido
            if (pacote.getId() == id) {
                // Remove o pacote da lista
                listapacotes.remove(i);
            }
        }
        String selectSql = "SELECT COUNT(*) FROM Pacote WHERE ID = ?";
        String deleteSql = "DELETE FROM Pacote WHERE ID = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {

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

    // busca de um pacote
    public Pacote buscarPacotePorId(int idPacote) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pacote pacote = null;

        try {
            String sql = "SELECT * FROM  Pacote  WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();

            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPacote);

            rs = ps.executeQuery();

            if (rs.next()) {
                pacote = construirPacote(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return pacote;
    }

    private Pacote construirPacote(ResultSet rs) throws SQLException {
        // Construa o objeto Pacote com os dados do ResultSet
        return new Pacote(
                rs.getInt("id"),
                rs.getInt("idDestino"),
                Categoria.valueOf(rs.getString("categoria")),
                rs.getInt("idHospedagem"),
                Pacote.TipoPassagem.valueOf(rs.getString("tipoPassagem")),
                rs.getInt("idPassagem"),
                rs.getInt("idAluguelCarro"),
                rs.getDouble("desconto"),
                rs.getDouble("preco"),
                rs.getDouble("mediaAvaliacoes"),
                rs.getInt("numAvaliacoes"));
    }

    // Método para fechar os recursos
    private void fecharRecursos(ResultSet rs, PreparedStatement ps, Connection conexao) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        }
    }
    // busca de um item em um pacote

    public AluguelCarro buscarAluguelCarroPorId(int idAluguelCarro) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AluguelCarro aluguelCarro = null;

        try {
            String sql = "SELECT * FROM AluguelCarro WHERE idAluguelCarro = ?";
            conexao = ConexaoMySQL.getConexao();

            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idAluguelCarro);

            rs = ps.executeQuery();

            if (rs.next()) {
                aluguelCarro = construirAluguelCarro(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return aluguelCarro;
    }

    private AluguelCarro construirAluguelCarro(ResultSet rs) throws SQLException {
        // Construa o objeto AluguelCarro com os dados do ResultSet
        return new AluguelCarro(
                rs.getInt("idAluguelCarro"),
                rs.getInt("numDiarias"),
                rs.getString("modeloCarro"),
                rs.getString("locadora"),
                rs.getTimestamp("retirada").toLocalDateTime(),
                rs.getString("enderecoRetirada"),
                rs.getString("enderecoDevolucao"),
                rs.getDouble("diaria"),
                rs.getInt("idSeguro"));
    }

    public Atividade buscarAtividadePorId(int idAtividade) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Atividade atividade = null;

        try {
            String sql = "SELECT * FROM Atividade WHERE idAtividade = ?";
            conexao = ConexaoMySQL.getConexao();

            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idAtividade);

            rs = ps.executeQuery();

            if (rs.next()) {
                atividade = construirAtividade(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return atividade;
    }

    private Atividade construirAtividade(ResultSet rs) throws SQLException {
        // Construa o objeto Atividade com os dados do ResultSet
        return new Atividade(
                rs.getInt("idAtividade"),
                rs.getString("nomeAtividade"),
                rs.getInt("idLocal"),
                rs.getString("descricao"),
                rs.getString("endereco"),
                rs.getTimestamp("inicio").toLocalDateTime(),
                rs.getTimestamp("fim").toLocalDateTime(),
                rs.getDouble("preco"));
    }

    public Comentario buscarComentarioHospedagemPorId(int idComentario) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Comentario comentario = null;

        try {
            String sql = "SELECT * FROM ComentariosHospedagens WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();

            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idComentario);

            rs = ps.executeQuery();

            if (rs.next()) {
                comentario = construirComentario(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return comentario;
    }

    public Comentario buscarComentarioLocalPorId(int idComentario) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Comentario comentario = null;

        try {
            String sql = "SELECT * FROM ComentariosLocalizacoes  WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();

            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idComentario);

            rs = ps.executeQuery();

            if (rs.next()) {
                comentario = construirComentario(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return comentario;
    }

    public Comentario buscarComentarioPacotePorId(int idComentario) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Comentario comentario = null;

        try {
            String sql = "SELECT * FROM ComentariosPacotes WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();

            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idComentario);

            rs = ps.executeQuery();

            if (rs.next()) {
                comentario = construirComentario(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return comentario;
    }

    private Comentario construirComentario(ResultSet rs) throws SQLException {
        // Construa o objeto Comentario com os dados do ResultSet
        return new Comentario(
                rs.getInt("id"),
                rs.getInt("cliente"),
                rs.getString("comentario"),
                rs.getTimestamp("data_e_hora_da_postagem").toLocalDateTime());
    }

    public Hospedagem buscarHospedagemPorId(int idHospedagem) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Hospedagem hospedagem = null;

        try {
            String sql = "SELECT * FROM Hospedagem WHERE id = ?";
            conexao = ConexaoMySQL.getConexao(); // Obtém a conexão usando o método da classe ConexaoMySQL

            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idHospedagem);

            rs = ps.executeQuery();

            if (rs.next()) {
                hospedagem = construirHospedagem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return hospedagem;
    }

    // Método auxiliar para criar uma instância de Hospedagem a partir do ResultSet
    private static Hospedagem construirHospedagem(ResultSet rs) throws SQLException {
        int idHospedagem = rs.getInt("id");
        String nome = rs.getString("nome");
        TipoHospedagem tipoHospedagem = TipoHospedagem.valueOf(rs.getString("tipo_hospedagem"));
        TipoSuite tipoSuite = TipoSuite.valueOf(rs.getString("tipo_suite"));
        TipoCama tipoCama = TipoCama.valueOf(rs.getString("tipo_cama"));
        String descricao = rs.getString("descricao");
        String endereco = rs.getString("endereco");
        int idLocal = rs.getInt("localizacao");
        LocalDateTime checkin = rs.getTimestamp("checkin").toLocalDateTime();
        LocalDateTime checkout = rs.getTimestamp("checkout").toLocalDateTime();
        double diaria = rs.getDouble("diaria");
        int numDiarias = rs.getInt("num_diarias");
        boolean disponivel = rs.getBoolean("disponivel");

        // Crie a instância de Hospedagem com os dados do ResultSet
        return new Hospedagem(idHospedagem, nome, tipoHospedagem, tipoSuite, tipoCama,
                descricao, endereco, idLocal, checkin, checkout, diaria, numDiarias, disponivel);
    }

    // Método auxiliar para buscar comentários de uma hospedagem pelo ID
    private List<Comentario> buscarComentariosPorHospedagem(int idHospedagem, Connection connection)
            throws SQLException {
        List<Comentario> comentarios = new ArrayList<>();
        String query = "SELECT * FROM ComentariosHospedagens WHERE hospedagem = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idHospedagem);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    comentarios.add(construirComentario(resultSet));
                }
            }
        }
        return comentarios;
    }

    public Local buscarLocalPorId(int idLocal) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Local local = null;

        try {
            String sql = "SELECT * FROM Localizacao WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idLocal);
            rs = ps.executeQuery();

            if (rs.next()) {
                local = construirLocal(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return local;
    }

    private Local construirLocal(ResultSet rs) throws SQLException {
        int idLocal = rs.getInt("id");
        String nome = rs.getString("nome");
        Local.Continente continente = Local.Continente.valueOf(rs.getString("continente"));
        double mediaAvaliacoes = rs.getDouble("mediaAvaliacoes");
        int numAvaliacoes = rs.getInt("numAvaliacoes");

        Local local = new Local(idLocal, nome, continente);
        local.setMediaAvaliacoes(mediaAvaliacoes);
        local.setNumAvaliacoes(numAvaliacoes);

        return local;
    }

    public Pagamento buscarPagamentoPorId(int idPagamento) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pagamento pagamento = null;

        try {
            String sql = "SELECT * FROM Pagamento WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPagamento);
            rs = ps.executeQuery();

            if (rs.next()) {
                pagamento = construirPagamento(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return pagamento;
    }

    private Pagamento construirPagamento(ResultSet rs) throws SQLException {
        int idPagamento = rs.getInt("id");
        int idUsuario = rs.getInt("cliente");
        double valor = rs.getDouble("valor");
        Pagamento.Situacao situacao = Pagamento.Situacao.valueOf(rs.getString("situacao"));
        LocalDate vencimento = rs.getDate("vencimento").toLocalDate();

        return new Pagamento(idPagamento, idUsuario, valor, situacao, vencimento);
    }

    public PassagemAerea buscarPassagemAereaPorId(int idPassagem) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PassagemAerea passagemAerea = null;

        try {
            String sql = "SELECT * FROM Pagamento WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPassagem);
            rs = ps.executeQuery();

            if (rs.next()) {
                passagemAerea = construirPassagemAerea(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return passagemAerea;
    }

    private PassagemAerea construirPassagemAerea(ResultSet rs) throws SQLException {
        int idPassagem = rs.getInt("id");
        int idPartida = rs.getInt("localPartida");
        int idDestino = rs.getInt("localChegada");
        LocalDateTime saida = rs.getTimestamp("saida").toLocalDateTime();
        LocalDateTime chegada = rs.getTimestamp("chegada").toLocalDateTime();
        double duracao = rs.getDouble("duracao");
        String companhia = rs.getString("companhia");
        double preco = rs.getDouble("preco");
        String aeroportoPartida = rs.getString("aeroporto_partida");
        String aeroportoChegada = rs.getString("aeroporto_chegada");
        String iataPartida = rs.getString("iataPartida");
        String iataChegada = rs.getString("iataDestino");

        return new PassagemAerea(idPassagem, idPartida, idDestino, saida, chegada, duracao,
                companhia, preco, aeroportoPartida, aeroportoChegada, iataPartida, iataChegada);
    }

    public PassagemOnibus buscarPassagemOnibusPorId(int idPassagem) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PassagemOnibus passagemOnibus = null;

        try {
            String sql = "SELECT * FROM PassagemOnibus WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPassagem);
            rs = ps.executeQuery();

            if (rs.next()) {
                passagemOnibus = construirPassagemOnibus(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return passagemOnibus;
    }

    private PassagemOnibus construirPassagemOnibus(ResultSet rs) throws SQLException {
        int idPassagem = rs.getInt("id");
        int idPartida = rs.getInt("localPartida");
        int idDestino = rs.getInt("localChegada");
        LocalDateTime saida = rs.getTimestamp("saida").toLocalDateTime();
        LocalDateTime chegada = rs.getTimestamp("chegada").toLocalDateTime();
        double duracao = rs.getDouble("duracao");
        String companhia = rs.getString("companhia");
        double preco = rs.getDouble("preco");
        String enderecoPartida = rs.getString("enderecoPartida");
        String enderecoChegada = rs.getString("enderecoChegada");

        return new PassagemOnibus(idPassagem, idPartida, idDestino, saida, chegada, duracao,
                companhia, preco, enderecoPartida, enderecoChegada);
    }

    public Reserva buscarReservaPorId(int idReserva) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reserva reserva = null;

        try {
            String sql = "SELECT * FROM Reserva WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idReserva);
            rs = ps.executeQuery();

            if (rs.next()) {
                reserva = construirReserva(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return reserva;
    }

    private Reserva construirReserva(ResultSet rs) throws SQLException {
        int idReserva = rs.getInt("id");
        int idPacote = rs.getInt("pacote");
        int idUsuario = rs.getInt("usuario");
        LocalDateTime entrada = rs.getTimestamp("entrada").toLocalDateTime();
        LocalDateTime saida = rs.getTimestamp("saida").toLocalDateTime();
        int idPagamento = rs.getInt("pagamento");
        double desconto = rs.getDouble("desconto");
        double preco = rs.getDouble("preco");

        return new Reserva(idReserva, idPacote, idUsuario, entrada, saida, idPagamento, desconto, preco);
    }

    public Seguro buscarSeguroPorId(int idSeguro) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Seguro seguro = null;

        try {
            String sql = "SELECT * FROM Seguro WHERE id = ?";
            conexao = ConexaoMySQL.getConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idSeguro);
            rs = ps.executeQuery();

            if (rs.next()) {
                seguro = construirSeguro(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return seguro;
    }

    private Seguro construirSeguro(ResultSet rs) throws SQLException {
        int idSeguro = rs.getInt("id");
        double franquia = rs.getDouble("franquia");
        String descricao = rs.getString("descricao");

        return new Seguro(idSeguro, franquia, descricao);
    }

    public List<Atividade> buscarAtividadesdoPacotePorId(int idPacote) {
        List<Atividade> lista = new ArrayList<>();

        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT a.* FROM AtividadesPacotes ap " +
                    "JOIN Atividade a ON ap.atividade = a.id " +
                    "WHERE ap.pacote = ?";
            conexao = ConexaoMySQL.getConexao();

            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPacote);

            rs = ps.executeQuery();

            while (rs.next()) {
                Atividade atividade = construirAtividade(rs);
                lista.add(atividade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } finally {
            fecharRecursos(rs, ps, conexao);
        }

        return lista;
    }

    // calcular pagamento
    public double somarpagamento(int Idpacote) {
        double total = 0.0;

        double precoHospedagem = buscarHospedagemPorId(buscarPacotePorId(Idpacote).getIdHospedagem()).getPreco();
        total += precoHospedagem;
        TipoPassagem tipo = buscarPacotePorId(Idpacote).getTipoPassagem();
        double precoPassagem = 0.0;
        switch (tipo) {
            case AEREA:
                precoPassagem = buscarPassagemAereaPorId(buscarPacotePorId(Idpacote).getIdPassagem()).getPreco();
                break;
            case ONIBUS:
                precoPassagem = buscarPassagemOnibusPorId(buscarPacotePorId(Idpacote).getIdPassagem()).getPreco();
                break;
            default:
                // Tratamento para qualquer outro caso (opcional)
                break;
        }
        total += precoPassagem;

        double precoAluguelCarro = buscarAluguelCarroPorId(buscarPacotePorId(Idpacote).getIdAluguelCarro()).getPreco();
        total += precoAluguelCarro;
        AluguelCarro aluguel = buscarAluguelCarroPorId(buscarPacotePorId(Idpacote).getIdAluguelCarro());
        double precoSeguro = buscarSeguroPorId(aluguel.getIdSeguro()).getFranquia();
        total += precoSeguro;
        double precoAtividades = 0.0;
        List<Atividade> lista = buscarAtividadesdoPacotePorId(Idpacote);
        for (Atividade atividade : lista) {
            precoAtividades += atividade.getPreco();
        }
        total += precoAtividades;

        return total;
    }

}