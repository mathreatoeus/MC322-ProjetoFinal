package models.pacote;

import java.time.LocalDateTime;
import models.usuario.Usuario;

public class Reserva {
    private int idReserva;
    // private Pacote pacote;
    private Usuario usuario;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private final Pagamento pagamento;
    private double desconto;
    private double preco;

    public Reserva(int idReserva, Usuario usuario, LocalDateTime entrada, LocalDateTime saida,
                   Pagamento pagamento, double desconto, double preco) {
        this.idReserva = idReserva;
        this.usuario = usuario;
        this.entrada = entrada;
        this.saida = saida;
        this.pagamento = pagamento;
        this.desconto = desconto;
        this.preco = preco;
    }

    public int getId() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

}
