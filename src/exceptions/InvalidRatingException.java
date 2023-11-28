package exceptions;

public class InvalidRatingException extends Exception {
    private final double avaliacao;

    public InvalidRatingException(String mensagem, double avaliacao) {
        super(mensagem);
        this.avaliacao = avaliacao;
    }

    public double getAvaliacao() {
        return avaliacao;
    }
}
