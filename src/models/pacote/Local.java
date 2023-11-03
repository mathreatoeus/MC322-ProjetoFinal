package models.pacote;

import models.usuario.GerarID;

public class Local {
    // Attributes -----------------------------------------------------------------------
    private final int idLocal;
    private final String nome;
    private double avaliacao;                                             // 0 - 5.

    // Constructor ----------------------------------------------------------------------
    public Local(String nome) {
        this.idLocal = GerarID.gerarId(nome);
        this.nome = nome;
        this.avaliacao = 0;
    }

    // Getters --------------------------------------------------------------------------
    public int getIdLocal() {
        return idLocal;
    }

    public String getNome() {
        return nome;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    // Setters --------------------------------------------------------------------------
    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }
}
