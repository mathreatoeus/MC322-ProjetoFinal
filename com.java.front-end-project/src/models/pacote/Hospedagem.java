package models.pacote;

import exceptions.CommentIsTooLongException;
import exceptions.InvalidRatingException;
import models.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Hospedagem {
    // Attributes -----------------------------------------------------------------------
    private final int idHospedagem;
    private final String nome;
    private final TipoHospedagem tipoHospedagem;
    private final TipoSuite tipoSuite;
    private final TipoCama tipoCama;
    private String descricao;
    private String endereco;
    private final int idLocal;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private final double diaria;
    private int numDiarias;
    private double preco;
    private double mediaAvaliacoes;
    private int numAvaliacoes;
    private final ArrayList<Comentario> comentarios;
    private boolean disponivel;

    // Constructor ----------------------------------------------------------------------
    public Hospedagem(int idHospedagem, String nome, TipoHospedagem tipoHospedagem,
                      TipoSuite tipoSuite, TipoCama tipoCama, String descricao, String endereco,
                      int idLocal, LocalDateTime checkin, LocalDateTime checkout, double diaria,
                      int numDiarias, boolean disponivel) {
        this.idHospedagem = idHospedagem;
        this.nome = nome;
        this.tipoHospedagem = tipoHospedagem;
        this.tipoSuite = tipoSuite;
        this.tipoCama = tipoCama;
        this.descricao = descricao;
        this.endereco = endereco;
        this.idLocal = idLocal;
        this.checkin = checkin;
        this.checkout = checkout;
        this.diaria = diaria;
        this.numDiarias = numDiarias;
        this.preco = diaria * numDiarias;
        this.mediaAvaliacoes = 0;                                       // Valor inicial.
        this.numAvaliacoes = 0;                                         // Valor inicial.
        this.comentarios = new ArrayList<>();
        this.disponivel = disponivel;
    }

    // Enum -----------------------------------------------------------------------------
    public enum TipoHospedagem {
        HOTEL, APARTAMENTO, CASA, ALBERGUE, POUSADA
    }

    public enum TipoSuite {
        INDIVIDUAL, DUPLA, TRIPLA, QUADRUPLA, PREMIUM
    }

    public enum TipoCama {
        SOLTEIRO, BELICHE, CASAL, QUEEN, KING
    }

    // Getters --------------------------------------------------------------------------
    public int getIdHospedagem() {
        return idHospedagem;
    }

    public String getNome() {
        return nome;
    }

    public TipoHospedagem getTipoHospedagem() {
        return tipoHospedagem;
    }

    public String getTipoSuite() {
        return tipoSuite.toString();
    }

    public TipoCama getTipoCama() {
        return tipoCama;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getIdLocal() {
        return idLocal;
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

    public boolean getDisponivel() {
        return disponivel;
    }

    // Setters --------------------------------------------------------------------------
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public void setNumDiarias(int numDiarias) {
        this.numDiarias = numDiarias;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public void setNumAvaliacoes(int numAvaliacoes) {
        this.numAvaliacoes = numAvaliacoes;
    }

    public void setDisponivel() {
        this.disponivel = !disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
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
     * Adiciona um comentario de ate 500 caracteres à lista de comentarios.
     *
     * @param idUsuario o id do usuario que fez o comentario.
     * @param mensagem a mensagem em si.
     * @throws CommentIsTooLongException se o comentario tiver mais do que 500 caracteres.
     */
    public void adicionarComentario(int idComentario, int idUsuario, String mensagem) throws CommentIsTooLongException {
        if (mensagem.length() < 500) {
            (this.comentarios).add(new Comentario(idComentario, idUsuario, mensagem, LocalDateTime.now()));
        }
        else {
            throw new CommentIsTooLongException("Comentario excede 500 caracteres.", mensagem);
        }
    }
}
