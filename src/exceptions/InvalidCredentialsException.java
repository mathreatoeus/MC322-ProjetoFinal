package exceptions;

import models.usuario.Usuario;

public class InvalidCredentialsException extends Exception {
    Usuario usuario;

    public InvalidCredentialsException(String mensagem, Usuario usuario) {
        super(mensagem);
        this.usuario = usuario;
    }
}
