package models.pacote;

import java.time.LocalDateTime;

public class Hospedagem {
    // Attributes -----------------------------------------------------------------------
    private final int idHospedagem;
    private final String hotel;
    // Usuario;
    private String tipoSuite;
    private String endereco;
    private final Local local;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private final double diaria;
    private int numDiarias;
    private double preco;
    private double avaliacao;

    // Constructor ----------------------------------------------------------------------
    public Hospedagem(String hotel, String tipoSuite, String endereco, Local local,
                      LocalDateTime checkin, LocalDateTime checkout, double diaria, int numDiarias,
                      double avaliacai, int idHospedagem) {
        this.idHospedagem = idHospedagem;
        this.hotel = hotel;
        this.tipoSuite = tipoSuite;
        this.endereco = endereco;
        this.local = local;
        this.checkin = checkin;
        this.checkout = checkout;
        this.diaria = diaria;
        this.numDiarias = numDiarias;
        this.preco = diaria * numDiarias;
        this.avaliacao = avaliacao;
    }

    // Getters --------------------------------------------------------------------------
    public int getIdHospedagem() {
        return idHospedagem;
    }

    public String getHotel() {
        return hotel;
    }

    public String getTipoSuite() {
        return tipoSuite;
    }

    public String getEndereco() {
        return endereco;
    }

    public Local getLocal() {
        return local;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    public LocalDateTime getCheckout() {
        return checkout;
    }

    public double getDiaria() {
        return diaria;
    }

    public int getNumDiarias() {
        return numDiarias;
    }

    public double getPreco() {
        return preco;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    // Setters --------------------------------------------------------------------------
    private void setTipoSuite(String tipoSuite) {
        this.tipoSuite = tipoSuite;
    }

    private void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    private void setCheckin(LocalDateTime checkin) {
        this.checkin = checkin;
    }

    private void setCheckout(LocalDateTime checkout) {
        this.checkout = checkout;
    }

    private void setNumDiarias(int numDiarias) {
        this.numDiarias = numDiarias;
    }

    private void setPreco(double preco) {
        this.preco = preco;
    }
}
