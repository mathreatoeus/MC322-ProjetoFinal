package models.usuario;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Usuario{
    private String numeroCartao;
    private LocalDate validade;
    private int cvv;
    private String nomeCartao;
    // private ArrayList<Reserva> reservas;
    
    
    private LocalDate dataRegistro;
    public Cliente(int idUsuario, String nome, LocalDate nascimento, String rg, String cpf, String email, String senha,
            String celular, String telefoneResidencial, String endereco, String numeroCartao, LocalDate validade,
            int cvv, String nomeCartao, LocalDate dataRegistro) {
        super(idUsuario, nome, nascimento, rg, cpf, email, senha, celular, telefoneResidencial, endereco);
        this.numeroCartao = numeroCartao;
        this.validade = validade;
        this.cvv = cvv;
        this.nomeCartao = nomeCartao;
        this.dataRegistro = dataRegistro;
    }
    public String getNumeroCartao() {
        return numeroCartao;
    }
    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
    public LocalDate getValidade() {
        return validade;
    }
    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
    public String getNomeCartao() {
        return nomeCartao;
    }
    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }
    public LocalDate getDataRegistro() {
        return dataRegistro;
    }
    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    

}
