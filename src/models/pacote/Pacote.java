package models.pacote;

import java.util.ArrayList;
import java.util.List;

public class Pacote {
    private int id;
   //colocar atributos da lista pacote
    private boolean fechado;

    // Constructor ----------------------------------------------------------------------
    public Pacote(){
        
        this.fechado = false;
    }
    
    // Getters --------------------------------------------------------------------------
    public int getId(){
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
