package models.pacote;

import java.time.LocalDateTime;

public class AluguelCarro implements ItemPacote {
    // Attributes -----------------------------------------------------------------------
    private final int idAluguelCarro;
    private int numDiarias;
    private String modeloCarro;
    private final String locadora;
    private LocalDateTime retirada;
    private LocalDateTime devolucao;
    private String enderecoRetirada;
    private String enderecoDevolucao;
    private double diaria;
    private double preco;
    private int idSeguro;

    // Constructor ----------------------------------------------------------------------
    public AluguelCarro(int idAluguelCarro, int numDiarias, String modeloCarro, String locadora,
                        LocalDateTime retirada, String enderecoRetirada, String enderecoDevolucao,
                        double diaria, int idSeguro) {
        this.idAluguelCarro = idAluguelCarro;
        this.numDiarias = numDiarias;
        this.modeloCarro = modeloCarro;
        this.locadora = locadora;
        this.retirada = retirada;
        this.devolucao = retirada.plusDays(numDiarias);
        this.enderecoRetirada = enderecoRetirada;
        this.enderecoDevolucao =enderecoDevolucao;
        this.diaria = diaria;
        this.preco = diaria * numDiarias;
        this.idSeguro = idSeguro;
    }

    // Getters --------------------------------------------------------------------------
    public int getId() {
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

    public double getDiaria() {
        return diaria;
    }

    public double getPreco() {
        return preco;
    }

    public int getIdSeguro() {
        return idSeguro;
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

    public void setDiaria(double diaria) {
        this.diaria = diaria;
    }

    private void setPreco(double preco) {
        this.preco = preco;
    }

    private void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }
}
