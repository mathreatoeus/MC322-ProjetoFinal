package models.pacote;

import java.time.LocalDate;

public abstract class Usuario {
    private final int idUsuario;
    private String nome;
    private LocalDate nascimento;
    private String rg;
    private String cpf;
    private String email;
    private String senha;
    private String celular;
    private String telefoneResidencial;
    private String endereco;

    public Usuario(int idUsuario, String nome, LocalDate nascimento, String rg, String cpf, String email, String senha,
            String celular, String telefoneResidencial, String endereco) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.nascimento = nascimento;
        this.rg = rg;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.celular = celular;
        this.telefoneResidencial = telefoneResidencial;
        this.endereco = endereco;
    }

    public boolean atualizarEmail(String emailAtual, String senha, String novoEmail) {
        return false;
    }

    public boolean atualizarSenha(String email, String SenhaAtual, String novaSenha) {
        return false;
    }

    public boolean atualizarTelefone(String email, String Senha, String novoTelefone) {
        return false;
    }

    public boolean atualizarEndereco(String email, String Senha, String novoEndereco) {
        return false;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

}
