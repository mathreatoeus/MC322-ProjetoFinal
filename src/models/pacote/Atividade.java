package models.pacote;

import java.time.LocalDateTime;

public class Atividade implements ItemPacote{
    // Attributes -----------------------------------------------------------------------
    private final int idAtividade;
    private final String nomeAtividade;
    private String descricao;
    private final Local local;
    private String endereco;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private double preco;

    // Constructor ----------------------------------------------------------------------
    public Atividade(int idAtividade, String nomeAtividade, Local local, String descricao,
                     String endereco, LocalDateTime inicio, LocalDateTime fim, double preco) {
        this.idAtividade = idAtividade;
        this.nomeAtividade = nomeAtividade;
        this.descricao = descricao;
        this.local = local;
        this.endereco = endereco;
        this.inicio = inicio;
        this.fim = fim;
        this.preco = preco;
    }

    // Getters --------------------------------------------------------------------------
    public int getId() {
        return idAtividade;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public String getDescricao() {
        return descricao;
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    private void setPreco(double preco) {
        this.preco = preco;
    }
}
