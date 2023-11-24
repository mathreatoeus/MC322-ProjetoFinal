package models.pacote;

import java.time.LocalDateTime;
import models.usuario.Usuario;

public class Reserva {
    private final int idReserva;
    private final int idPacote;
    private int idUsuario;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private final int idPagamento;
    private double desconto;
    private double preco;

    public Reserva(int idReserva, int idPacote, int idUsuario, LocalDateTime entrada,
                   LocalDateTime saida, int idPagamento, double desconto, double preco) {
        this.idReserva = idReserva;
        this.idPacote = idPacote;
        this.idUsuario = idUsuario;
        this.entrada = entrada;
        this.saida = saida;
        this.idPagamento = idPagamento;
        this.desconto = desconto;
        this.preco = preco;
    }

    public int getId() {
        return idReserva;
    }

    public int getIdPacote() {
        return idPacote;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public int getIdPagamento() {
        return idPagamento;
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
