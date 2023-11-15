package models.pacote;

import java.time.LocalDateTime;
import models.usuario.Usuario;

public class Pagamento implements ItemPacote{
    private int idPagamento;
    private Usuario usuario;
    private double quantia;
    private LocalDateTime prazo;

    // private StatusPagamento status;
    public Pagamento(int idPagamento, Usuario usuario, double quantia, LocalDateTime prazo) {
        this.idPagamento = idPagamento;
        this.usuario = usuario;
        this.quantia = quantia;
        this.prazo = prazo;
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

    public double getQuantia() {
        return quantia;
    }

    public void setQuantia(double quantia) {
        this.quantia = quantia;
    }

    public LocalDateTime getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDateTime prazo) {
        this.prazo = prazo;
    }

}
