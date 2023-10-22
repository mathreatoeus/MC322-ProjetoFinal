package exceptions;

public class InvalidCredentialsException extends Exception {
    // User comes here...

    public InvalidCredentialsException(String mensagem) {
        super(mensagem);
    }
}
