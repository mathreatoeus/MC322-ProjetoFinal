package models.pacote;

import java.time.LocalDate;
import models.usuario.Usuario;

public class Pagamento {
    private int idPagamento;
    private Usuario usuario;
    private double valor;
    private Situacao situacao;
    private LocalDate vencimento;

    // private StatusPagamento status;
    public Pagamento(int idPagamento, Usuario usuario, double valor, Situacao situacao,
                     LocalDate vencimento) {
        this.idPagamento = idPagamento;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
