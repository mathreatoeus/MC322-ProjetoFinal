package models.pacote;

import java.time.LocalDateTime;

public class PassagemAerea extends Passagem {
    // Attributes -----------------------------------------------------------------------
    private String aeroportoPartida;
    private String aeroportoChegada;
    private String iataPartida;
    private String iataChegada;

    // OBS: código IATA (International Air Transport Assiciation) é um código de 3 letras
    // utilizado para identificar aeroportos (Ex: Guarulhos --> GRU).

    // Constructor ----------------------------------------------------------------------
    public PassagemAerea(Local partida, Local destino, LocalDateTime saida, LocalDateTime chegada,
                         double duracao, String companhia, double preco, String aeroportoPartida,
                         String aeroportoChegada, String iataPartida, String iataChegada) {
        super(partida, destino, saida, chegada, duracao, companhia, preco);
        this.aeroportoPartida = aeroportoPartida;
        this.aeroportoChegada = aeroportoChegada;
        this.iataPartida = iataPartida;
        this.iataChegada = iataChegada;
    }

    // Getters --------------------------------------------------------------------------
    public String getAeroportoPartida() {
        return aeroportoPartida;
    }

    public String getAeroportoChegada() {
        return aeroportoChegada;
    }

    public String getIataPartida() {
        return iataPartida;
    }

    public String getIataChegada() {
        return iataChegada;
    }

    // Setters --------------------------------------------------------------------------
    private void setAeroportoPartida(String aeroportoPartida) {
        this.aeroportoPartida = aeroportoPartida;
    }

    private void setAeroportoChegada(String aeroportoChegada) {
        this.aeroportoChegada = aeroportoChegada;
    }

    private void setIataPartida(String iataPartida) {
        this.iataPartida = iataPartida;
    }

    private void setIataChegada(String iataChegada) {
        this.iataChegada = iataChegada;
    }
}
