package models.pacote;

import java.util.ArrayList;
import java.util.List;

public class Pacote {
    private List<ItemPacote> itensPacote ;
    private boolean fechado;

    // Constructor ----------------------------------------------------------------------
    public Pacote(){
        this.itensPacote = new ArrayList<>();
        this.fechado = false;
    }
    
    // Getters --------------------------------------------------------------------------
    public boolean getFechado(){
        return fechado;
    }

    public List<ItemPacote> getItemPacotes(){
        return itensPacote;
    }
    
    // Setters --------------------------------------------------------------------------
    public void setFechado(boolean fechado){
        this.fechado = fechado;
    }

    // Methods --------------------------------------------------------------------------
    private double calcularPrecoPacote() {
        // Logica para calculo da franquia...
        return 0.0;
    }

  
}
