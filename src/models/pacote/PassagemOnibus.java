package models.pacote;

import java.time.LocalDateTime;

public class PassagemOnibus extends Passagem{
    // Attributes -----------------------------------------------------------------------
    private String enderecoPartida;
    private String enderecoChegada;

    // Constructor ----------------------------------------------------------------------
    public PassagemOnibus(int idPassagem, int idPartida, int idDestino, LocalDateTime saida, LocalDateTime chegada,
                          double duracao, String companhia, double preco, String enderecoPartida,
                          String enderecoChegada) {
        super(idPassagem, idPartida, idDestino, saida, chegada, duracao, companhia, preco);
        this.enderecoPartida = enderecoPartida;
        this.enderecoChegada = enderecoChegada;
    }

    // Getters --------------------------------------------------------------------------
    public String getEnderecoPartida() {
        return enderecoPartida;
    }

    public String getEnderecoChegada() {
        return enderecoChegada;
    }

    // Setters --------------------------------------------------------------------------
    private void setEnderecoPartida(String enderecoPartida) {
        this.enderecoPartida = enderecoPartida;
    }

    private void setEnderecoChegada(String enderecoChegada) {
        this.enderecoChegada = enderecoChegada;
    }
}
