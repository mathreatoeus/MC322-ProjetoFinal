package models.pacote;

public class Seguro {
    // Attributes -----------------------------------------------------------------------
    private double franquia;
    // Usuario vem aqui

    // Constructor ----------------------------------------------------------------------
    public Seguro() {
        this.franquia = calcularFranquia();
    }

    // Getters --------------------------------------------------------------------------
    private double getFranquia() {
        return franquia;
    }

    // Setters --------------------------------------------------------------------------
    private void setFranquia(double franquia) {
        this.franquia = franquia;
    }

    // Methods --------------------------------------------------------------------------
    private double calcularFranquia() {
        // Logica para calculo da franquia...
        return 0.0;
    }
}
