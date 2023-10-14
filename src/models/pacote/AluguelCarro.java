package models.pacote;

import models.utils.GerarID;
import models.pacote.Seguro;
import java.time.LocalDateTime;

public class AluguelCarro {
    // Attributes -----------------------------------------------------------------------
    private final int idAluguelCarro;
    private int numDiarias;
    private String modeloCarro;
    private final String locadora;
    private LocalDateTime retirada;
    private LocalDateTime devolucao;
    private String enderecoRetirada;
    private String enderecoDevolucao;
    private double preco;
    private Seguro seguro;

    // Constructor ----------------------------------------------------------------------
    public AluguelCarro(int numDiarias, String modeloCarro, String locadora, LocalDateTime retirada,
                        String enderecoRetirada, String enderecoDevolucao, double diaria,
                        Seguro seguro) {
        this.idAluguelCarro = GerarID.gerarId(modeloCarro + retirada.toString());
        this.numDiarias = numDiarias;
        this.modeloCarro = modeloCarro;
        this.locadora = locadora;
        this.retirada = retirada;
        this.devolucao = retirada.plusDays(numDiarias);
        this.enderecoRetirada = enderecoRetirada;
        this.enderecoDevolucao =enderecoDevolucao;
        this.preco = diaria * numDiarias;
        this.seguro = seguro;
    }

    // Getters --------------------------------------------------------------------------
    public int getIdAluguelCarro() {
        return idAluguelCarro;
    }

    public int getNumDiarias() {
        return numDiarias;
    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public String getLocadora() {
        return locadora;
    }

    public LocalDateTime getRetirada() {
        return retirada;
    }

    public LocalDateTime getDevolucao() {
        return devolucao;
    }

    public String getEnderecoRetirada() {
        return enderecoRetirada;
    }

    public String getEnderecoDevolucao() {
        return enderecoDevolucao;
    }

    public double getPreco() {
        return preco;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    // Setters --------------------------------------------------------------------------
    private void setNumDiarias(int numDiarias) {
        this.numDiarias = numDiarias;
    }

    private void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    private void setRetirada(LocalDateTime retirada) {
        this.retirada = retirada;
    }

    private void setDevolucao(LocalDateTime devolucao) {
        this.devolucao = devolucao;
    }

    private void setEnderecoRetirada(String enderecoRetirada) {
        this.enderecoRetirada = enderecoRetirada;
    }

    private void setEnderecoDevolucao(String enderecoDevolucao) {
        this.enderecoDevolucao = enderecoDevolucao;
    }

    private void setPreco(double preco) {
        this.preco = preco;
    }

    private void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }


}
