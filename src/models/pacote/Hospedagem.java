package models.pacote;

import exceptions.InvalidRatingException;
import models.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Hospedagem {
    // Attributes -----------------------------------------------------------------------
    private final int idHospedagem;
    private final String hotel;
    private TipoSuite tipoSuite;
    private String endereco;
    private final Local local;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private final double diaria;
    private int numDiarias;
    private double preco;
    private double mediaAvaliacoes;
    private int numAvaliacoes;
    private final ArrayList<Comentario> comentarios;

    // Constructor ----------------------------------------------------------------------
    public Hospedagem(String hotel, TipoSuite tipoSuite, String endereco, Local local,
                      LocalDateTime checkin, LocalDateTime checkout, double diaria, int numDiarias,
                      int idHospedagem) {
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
        this.mediaAvaliacoes = 0;                                       // Valor inicial.
        this.numAvaliacoes = 0;                                         // Valor inicial.
        this.comentarios = new ArrayList<>();
    }

    // Enum -----------------------------------------------------------------------------
    public enum TipoSuite {
        INDIVIDUAL, DUPLA, TRIPLA, QUADRUPLA, PREMIUM
    }

    // Getters --------------------------------------------------------------------------
    public int getIdHospedagem() {
        return idHospedagem;
    }

    public String getHotel() {
        return hotel;
    }

    public String getTipoSuite() {
        return tipoSuite.toString();
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
        return mediaAvaliacoes;
    }

    public int getNumAvaliacoes() {
        return numAvaliacoes;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    // Setters --------------------------------------------------------------------------
    private void setTipoSuite(TipoSuite tipoSuite) {
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

    private void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    private void setNumAvaliacoes(int numAvaliacoes) {
        this.numAvaliacoes = numAvaliacoes;
    }

    // Methods --------------------------------------------------------------------------
    /**
     * Submete uma avaliacao de usuario e atualiza a media e o numero de avaliacoes.
     *
     * @param avaliacao a avaliacao do usuario.
     * @throws InvalidRatingException se a avaliacao for maior que 5 ou menor que 0.
     */
    public void avaliar(double avaliacao) throws InvalidRatingException {
        if (avaliacao > 5.0 || avaliacao < 0) {
            throw new InvalidRatingException("Avaliacao invalida (maior que 5 ou menor que 0).",
                    avaliacao);
        }
        else {
            // mediaAvaliacoes = somatorioAvaliacoes / numAvaliacoes.
            double novoSomatorio = (this.mediaAvaliacoes * this.numAvaliacoes) + avaliacao;
            this.numAvaliacoes++;
            this.mediaAvaliacoes = novoSomatorio / this.numAvaliacoes;
        }
    }

    /**
     * Adiciona um comentario à lista de comentarios.
     *
     * @param usuario o usuario que fez o comentario.
     * @param mensagem a mensagem do comentario.
     */
    public void adicionarComentario(Usuario usuario, String mensagem) {
        this.comentarios.add(new Comentario(usuario, mensagem));
    }
}
