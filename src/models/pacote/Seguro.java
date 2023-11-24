package models.pacote;

public class Seguro {
    // Attributes -----------------------------------------------------------------------
    private double franquia;
    private final String descricao;

    // Constructor ----------------------------------------------------------------------
    public Seguro(double franquia, String descricao) {
        this.franquia = franquia;
        this.descricao = descricao;
    }

    // Getters --------------------------------------------------------------------------
    private double getFranquia() {
        return franquia;
    }

    public String getDescricao() {
        return descricao;
    }

    // Setters --------------------------------------------------------------------------
    private void setFranquia(double franquia) {
        this.franquia = franquia;
    }
}
