package models.pacote;

import java.time.LocalDateTime;

public abstract class Passagem implements ItemPacote{
    // Attributes -----------------------------------------------------------------------
    private final int idPassagem;
    private final int idPartida;
    private final int idDestino;
    private LocalDateTime saida;
    private LocalDateTime chegada;
    private double duracao;                                                     // Horas.
    private String companhia;
    private double preco;

    // Constructor ----------------------------------------------------------------------
    public Passagem(int idPassagem, int idPartida, int idDestino, LocalDateTime saida,
                    LocalDateTime chegada, double duracao, String companhia, double preco) {
        this.idPassagem = idPassagem;
        this.idPartida = idPartida;
        this.idDestino = idDestino;
        this.saida = saida;
        this.chegada = chegada;
        this.duracao = duracao;
        this.companhia = companhia;
        this.preco = preco;
    }

    // Getters --------------------------------------------------------------------------
    public int getId() {
        return idPassagem;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public LocalDateTime getChegada() {
        return chegada;
    }

    public double getDuracao() {
        return duracao;
    }

    public String getCompanhia() {
        return companhia;
    }

    public double getPreco() {
        return preco;
    }

    // Setters --------------------------------------------------------------------------
    private void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    private void setChegada(LocalDateTime chegada) {
        this.chegada = chegada;
    }

    private void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    private void setCompanhia(String companhia) {
        this.companhia = companhia;
    }

    private void setPreco(double preco) {
        this.preco = preco;
    }
}
