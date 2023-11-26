package models.pacote;

import java.time.LocalDateTime;

public class Comentario {
    // Attributes -----------------------------------------------------------------------
    private final int id;
    private final int idUsuario;
    private final LocalDateTime dataEHoraDaPostagem;
    private final String comentario;

    // Constructor ----------------------------------------------------------------------
    public Comentario(int id, int idUsuario, String comentario, LocalDateTime localDateTime) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.dataEHoraDaPostagem = localDateTime;
        this.comentario = comentario;
        
    }

    // Getters --------------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public int getUsuario() {
        return idUsuario;
    }

    public LocalDateTime getDataEHoraDaPostagem() {
        return dataEHoraDaPostagem;
    }

    public String getComentario() {
        return comentario;
    }

    
}
