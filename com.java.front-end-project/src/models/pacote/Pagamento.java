package models.pacote;

import java.time.LocalDate;
import models.usuario.Usuario;

public class Pagamento {
    private int idPagamento;
    private int idUsuario;
    private double valor;
    private Situacao situacao;
    private LocalDate vencimento;

    // private StatusPagamento status;
    public Pagamento(int idPagamento, int idUsuario, double valor, Situacao situacao,
                     LocalDate vencimento) {
        this.idPagamento = idPagamento;
        this.idUsuario = idUsuario;
        this.valor = valor;
        this.situacao = situacao;
        this.vencimento = vencimento;
    }

    public enum Situacao {
        PENDENTE, PAGO, ATRASADO
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setPrazo(LocalDate vencimento) {
        this.vencimento = vencimento;
    }
}
