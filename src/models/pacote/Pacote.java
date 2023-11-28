package models.pacote;

import exceptions.CommentIsTooLongException;
import exceptions.InvalidRatingException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pacote {
    private final int id;
    private final int idDestino;
    private final Categoria categoria;
    private int idHospedagem;
    private TipoPassagem tipoPassagem;
    private int idPassagem;
    private int idAluguelCarro;
    private double desconto;
    private double preco;
    private double mediaAvaliacoes;
    private int numAvaliacoes;
    private final ArrayList<Comentario> comentarios;
    private boolean fechado;

    // Constructor ----------------------------------------------------------------------
    // Informando mediaAvaliacoes e numAvaliacoes manualmente...
    public Pacote(int id, int idDestino, Categoria categoria, int idHospedagem,
                  TipoPassagem tipoPassagem, int idPassagem, int idAluguelCarro, double desconto,
                  double preco, double mediaAvaliacoes, int numAvaliacoes){
        this.id = id;
        this.idDestino = idDestino;
        this.categoria = categoria;
        this.idHospedagem = idHospedagem;
        this.tipoPassagem = tipoPassagem;
        this.idPassagem = idPassagem;
        this.idAluguelCarro = idAluguelCarro;
        this.desconto = desconto;
        this.preco = preco;
        this.mediaAvaliacoes = mediaAvaliacoes;
        this.numAvaliacoes = numAvaliacoes;
        this.comentarios = new ArrayList<>();
        this.fechado = false;
    }

    // mediaAvaliacoes e numAvaliacoes assumem valores default (0.0 e 0)...
    public Pacote(int id, int idDestino, Categoria categoria, int idHospedagem, TipoPassagem tipoPassagem,
                  int idPassagem, int idAluguelCarro, double desconto, double preco) {
        this.id = id;
        this.idDestino = idDestino;
        this.categoria = categoria;
        this.idHospedagem = idHospedagem;
        this.tipoPassagem = tipoPassagem;
        this.idPassagem = idPassagem;
        this.idAluguelCarro = idAluguelCarro;
        this.desconto = desconto;
        this.preco = preco;
        this.mediaAvaliacoes = 0.0;
        this.numAvaliacoes = 0;
        this.comentarios = new ArrayList<>();
        this.fechado = false;
    }

    // Enum -----------------------------------------------------------------------------
    public enum TipoPassagem {
        AEREA, ONIBUS
    }

    public enum Categoria {
        AVENTURA, CULTURA, RELAXAMENTO, HISTORICO, VIDA_NOTURNA, GASTRONOMIA, FAMILIA, CRUZEIRO,
        FESTIVAL
    }

    // Getters --------------------------------------------------------------------------
    public int getId(){
        return id;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getIdHospedagem() {
        return idHospedagem;
    }

    public TipoPassagem getTipoPassagem() {
        return tipoPassagem;
    }

    public int getIdPassagem() {
        return idPassagem;
    }

    public int getIdAluguelCarro() {
        return idAluguelCarro;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getPreco() {
        return preco;
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

    public boolean getFechado(){
        return fechado;
    }

    // Setters --------------------------------------------------------------------------
    public void setIdHospedagem(int idHospedagem) {
        this.idHospedagem = idHospedagem;
    }

    public void setTipoPassagem(TipoPassagem tipoPassagem) {
        this.tipoPassagem = tipoPassagem;
    }

    public void setIdPassagem(int idPassagem) {
        this.idPassagem = idPassagem;
    }

    public void setIdAluguelCarro(int idAluguelCarro) {
        this.idAluguelCarro = idAluguelCarro;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    private void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    private void setNumAvaliacoes(int numAvaliacoes) {
        this.numAvaliacoes = numAvaliacoes;
    }

    public void setFechado(boolean fechado){
        this.fechado = fechado;
    }

    // Methods --------------------------------------------------------------------------

    /**
     * Submete uma avaliacao de usuario e atualiza mediaAvaliacoes e numAvaliacoes.
     *
     * @param avaliacao a avaliacao do usuario.
     * @throws InvalidRatingException se a avaliacao for maior que 5.0 ou menor que 0.0.
     */
    public void avaliar(double avaliacao) throws InvalidRatingException {
        if (avaliacao > 5.0 || avaliacao < 0) {
            throw new InvalidRatingException("Avaliacao invalida (maior que 5 ou menor que 0).",
                                             avaliacao);
        }
        else {
            // mediaAvaliacoes = somatorioAvaliacoes / numAvaliacoes
            double novoSomatorio = (this.mediaAvaliacoes * this.numAvaliacoes) + avaliacao;
            this.numAvaliacoes++;
            this.mediaAvaliacoes = novoSomatorio / this.numAvaliacoes;
        }
    }

    /**
     * Adiciona um comentario de ate 500 caracteres a lista de comentarios.
     *
     * @param idUsuario o id do usuario que fez o comentario.
     * @param mensagem a mensagem.
     * @throws CommentIsTooLongException se a mensagem tiver mais do que 500 caracteres.
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
