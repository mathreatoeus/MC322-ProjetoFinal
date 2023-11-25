package models.pacote;

public class Seguro {
    // Attributes -----------------------------------------------------------------------
    private final int id;
    private double franquia;
    private String descricao;

    // Constructor ----------------------------------------------------------------------
    public Seguro(int id, double franquia, String descricao) {
        this.id = id;
        this.franquia = franquia;
        this.descricao = descricao;
    }

    // Getters --------------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public double getFranquia() {
        return franquia;
    }

    public String getDescricao() {
        return descricao;
    }

    // Setters --------------------------------------------------------------------------
    private void setFranquia(double franquia) {
        this.franquia = franquia;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
