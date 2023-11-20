package models.pacote;

import java.util.ArrayList;
import java.util.List;

public class Pacote {
    private String id;
    private boolean fechado;

    // Constructor ----------------------------------------------------------------------
    public Pacote(){
        
        this.fechado = false;
    }
    
    // Getters --------------------------------------------------------------------------
    public String getId(){
        return id;
    }
    
    public boolean getFechado(){
        return fechado;
    }

    // Setters --------------------------------------------------------------------------
    public void setFechado(boolean fechado){
        this.fechado = fechado;
    }

  
}
