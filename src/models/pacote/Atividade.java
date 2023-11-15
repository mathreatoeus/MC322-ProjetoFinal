package models.pacote;

import models.usuario.GerarID;

import java.time.LocalDateTime;

public class Atividade implements ItemPacote{
    // Attributes -----------------------------------------------------------------------
    private final int idAtividade;
    private final String nomeAtividade;
    private final Local local;
    private String endereco;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private double preco;

    // Constructor ----------------------------------------------------------------------
    public Atividade(String nomeAtividade, Local local, String endereco, LocalDateTime inicio,
                     LocalDateTime fim, double preco) {
        this.idAtividade = GerarID.gerarId(nomeAtividade);
        this.nomeAtividade = nomeAtividade;
        this.local = local;
        this.endereco = endereco;
        this.inicio = inicio;
        this.fim = fim;
        this.preco = preco;
    }

    // Getters --------------------------------------------------------------------------
    
    public int getIdAtividade() {
        return idAtividade;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public Local getLocal() {
        return local;
    }

    public String getEndereco() {
        return endereco;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    
    public double getPreco() {
        return preco;
        
    }

    // Setters --------------------------------------------------------------------------
    private void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    private void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    private void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    private void setPreco(double preco) {
        this.preco = preco;
    }
}
