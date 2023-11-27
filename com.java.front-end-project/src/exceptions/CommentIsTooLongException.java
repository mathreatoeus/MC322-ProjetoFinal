package exceptions;

public class CommentIsTooLongException extends Exception {
    private final String comentario;

    public CommentIsTooLongException(String mensagem, String comentario) {
        super(mensagem);
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }
}
