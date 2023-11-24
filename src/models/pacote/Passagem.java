package models.pacote;

import java.time.LocalDateTime;

public abstract class Passagem implements ItemPacote{
    // Attributes -----------------------------------------------------------------------
    private final int idPassagem;
    private final Local partida;
    private final Local destino;
    private LocalDateTime saida;
    private LocalDateTime chegada;
    private double duracao;                                                     // Horas.
    private String companhia;
    private double preco;

    // Constructor ----------------------------------------------------------------------
    public Passagem(int idPassagem, Local partida, Local destino, LocalDateTime saida,
                    LocalDateTime chegada, double duracao, String companhia, double preco) {
        this.idPassagem = idPassagem;
        this.partida = partida;
        this.destino = destino;
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

    public Local getPartida() {
        return partida;
    }

    public Local getDestino() {
        return destino;
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
