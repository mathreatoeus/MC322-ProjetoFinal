package models.pacote;

import models.usuario.Usuario;

import java.time.LocalDateTime;

public class Comentario implements ItemPacote{
    // Attributes -----------------------------------------------------------------------
    private final Usuario usuario;
    private final LocalDateTime dataEHoraDaPostagem;
    private final String comentario;

    // Constructor ----------------------------------------------------------------------
    public Comentario(Usuario usuario, String comentario) {
        this.usuario = usuario;
        this.dataEHoraDaPostagem = LocalDateTime.now();
        this.comentario = comentario;
    }

    // Getters --------------------------------------------------------------------------
    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getDataEHoraDaPostagem() {
        return dataEHoraDaPostagem;
    }

    public String getComentario() {
        return comentario;
    }
}
