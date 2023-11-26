package exceptions;

import models.usuario.Usuario;

public class NewCredentialEqualsPreviousException extends Exception{
    private final Usuario usuario;
    private final String credencialErrada;
    private String mensagem;

    public NewCredentialEqualsPreviousException(Usuario usuario, String credencialErrada,
                                                String mensagem) {
        super(mensagem);
        this.credencialErrada = credencialErrada;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getCredencialErrada() {
        return credencialErrada;
    }
}
