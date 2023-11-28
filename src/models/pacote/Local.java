package models.pacote;

import exceptions.InvalidRatingException;
import models.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Local {
    // Attributes -----------------------------------------------------------------------
    private final int idLocal;
    private final String nome;
    private final Continente continente;
    private double mediaAvaliacoes;                                             // 0 - 5.
    private int numAvaliacoes;
    private final ArrayList<Comentario> comentarios;

    // Constructor ----------------------------------------------------------------------
    public Local(int idLocal, String nome, Continente continente) {
        this.idLocal = idLocal;
        this.nome = nome;
        this.continente = continente;
        this.mediaAvaliacoes = 0;                                       // Valor inicial.
        this.numAvaliacoes = 0;                                         // Valor inicial.
        this.comentarios = new ArrayList<>();
    }

    public enum Continente {
        AMERICA_DO_SUL, AMERICA_CENTRAL, AMERICA_DO_NORTE, ASIA, OCEANIA, EUROPA, ORIENTE_MEDIO,
        AFRICA
    }

    // Getters --------------------------------------------------------------------------
    public int getIdLocal() {
        return idLocal;
    }

    public String getNome() {
        return nome;
    }

    public Continente getContinente() {
        return continente;
    }

    public double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public int getNumAvaliacoes() {
        return numAvaliacoes;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    // Setters --------------------------------------------------------------------------
    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public void setNumAvaliacoes(int numAvaliacoes) {
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
        if (avaliacao > 5 || avaliacao < 0) {
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
     * @param idUsuario o id do usuario que fez o comentario.
     * @param mensagem a mensagem do comentario.
     */
    public void adicionarComentario(int idComentario, int idUsuario, String mensagem) {
        this.comentarios.add(new Comentario(idComentario, idUsuario, mensagem, LocalDateTime.now()));
    }
}
