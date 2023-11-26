package controllers;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.PackageNotClosedException;
import models.pacote.*;
import models.usuario.Cliente;
import models.usuario.Funcionario;
import models.usuario.Usuario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UsuarioControllerImpl {

    /* metodo para cadastrar usuário tipo cliente */

    public void cadastrarCliente(Cliente usuario) {

        /* Verificar se já existe um usuário com mesmo CPF ou e-mail */
        if (clienteExiste(usuario.getCpf(), usuario.getEmail())) {
            /* lançar uma mensagem no Front-end --> COMO FAZ ISSO??? */
            return;
        }
        String sql = "INSERT INTO Cliente (NOME, NASCIMENTO, CPF, EMAIL, SENHA, CELULAR, ENDERECO, NUMERO_CARTAO, VALIDADE, CVV, NOME_CARTAO, DATA_REGISTRO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            /* lançar uma mensagem no Front-end */
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
     * verificação de credenciais do funcionario - retorno deve ser usado pelo
     * Front-end
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

    /* exclusão de cliente do banco de dados */
    public void excluirCliente(String cpf) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Cliente WHERE CPF = ?";
        String deleteSql = "DELETE FROM Cliente WHERE CPF = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setString(1, cpf);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                /* Item encontrado */
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setString(1, cpf);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException(
                                "Falha ao excluir o usuário com CPF " + cpf + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("usuário com CPF " + cpf + " não encontrado.");
            }
        }
    }

    /* exclusão de funcionario do banco de dados */
    public void excluirFuncionario(String cpf) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM Funcionario WHERE CPF = ?";
        String deleteSql = "DELETE FROM Funcionario WHERE CPF = ?";

        try (PreparedStatement selectStatement = ConexaoMySQL.getConexao().prepareStatement(selectSql)) {
            selectStatement.setString(1, cpf);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                /* Item encontrado */
                try (PreparedStatement deleteStatement = ConexaoMySQL.getConexao().prepareStatement(deleteSql)) {
                    deleteStatement.setString(1, cpf);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        return;
                    } else {
                        throw new SQLException(
                                "Falha ao excluir o usuário com CPF " + cpf + ". Nenhuma linha afetada.");
                    }
                }
            } else {
                throw new SQLException("usuário com CPF " + cpf + " não encontrado.");
            }
        }
    }

    //efetuação de pagamento
    public void pagar(Cliente cliente, Reserva reserva) throws PackageNotClosedException{
       Pacote pacote = new PacoteControllerImpl().buscarPacotePorId(reserva.getIdPacote());
       Pagamento pagamento = new PacoteControllerImpl().buscarPagamentoPorId(reserva.getIdPagamento());
        if (pacote.getFechado()== false) { 
           switch (pagamento.getSituacao()) {
            case PENDENTE:
                double preco = pacote.getPreco();
                double desconto = pacote.getDesconto();
                double total = preco - desconto;
                pagamento.setValor(total);
                pagamento.setSituacao(Pagamento.Situacao.PAGO);
                pacote.setPreco(0.00);
                pacote.setFechado(false);
                
                break;
           
            case PAGO:
                //já está pago, nada acontece
                break;
            
            case ATRASADO:// se estiver em atraso, o desconto é descartado e uma multa é aplicada de acordo com a quantidade de dias
                long diasAtraso = ChronoUnit.DAYS.between(pagamento.getVencimento(), LocalDate.now());
               double preco1= 0.0;
                if( diasAtraso<10){
                    preco1= pacote.getPreco() + (10*diasAtraso);
                }else if(diasAtraso<25){
                     preco1= pacote.getPreco() + (25*diasAtraso);
                }else if(diasAtraso<50){
                     preco1= pacote.getPreco() + (50*diasAtraso);
                }
                
                pagamento.setValor(preco1);
                pagamento.setSituacao(Pagamento.Situacao.PAGO);
                pacote.setPreco(0.00);
                pacote.setFechado(false);
                
                break;
            
            default:
                break;
           }
        } else {
            throw new PackageNotClosedException("Não é possível fazer o pagamento. O pacote já está fechado.");
        }
    }
}
