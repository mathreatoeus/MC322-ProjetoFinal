package controllers;

import java.util.ArrayList;
import java.util.List;
import models.pacote.*;

public class PacoteControllerImpl {
    List<Pacote> ListaPacotes = new ArrayList<>();

    public Pacote criarPacote(){

        Pacote pacote = new Pacote();// como inserir no banco ao invés de criar???
        ListaPacotes.add(pacote);

        return pacote;

    }

    public void editarPacote(Pacote pacote){
        
    }

    public boolean PacoteExite(Pacote pacote){
        if(ListaPacotes.contains(pacote)){
            return true;
        }else{
            return false;
        }
    }
}
