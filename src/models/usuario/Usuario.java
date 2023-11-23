package models.usuario;

import exceptions.InvalidCredentialsException;
import exceptions.NewCredentialEqualsPreviousException;

import java.time.LocalDate;

public abstract class Usuario {
    // Attributes -----------------------------------------------------------------------
    private final int idUsuario;
    private String nome;
    private LocalDate nascimento;
    private String cpf;
    private String email;
    private String senha;
    private String celular;
    private String endereco;

    // Constructor ----------------------------------------------------------------------

    public Usuario(int idUsuario, String nome, LocalDate nascimento, String cpf,
                   String email, String senha, String celular, String endereco) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.celular = celular;
        this.endereco = endereco;
    }

    // Getters and Setters --------------------------------------------------------------
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    // Methods --------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Nome: " + this.nome + "\n" +
               "Email: " + this.email + "\n" +
               "Celular: " + this.celular + "\n";
    }

    /**
     * Tenta atualizar o email do usuario mediante autenticação.
     *
     * @param emailAtual o email atual do usuario.
     * @param senha a senha do usuario.
     * @param novoEmail o novo email desejado.
     * @return true se a operacao for bem-sucedida.
     * @throws NewCredentialEqualsPreviousException se o novo email for igual ao anterior.
     * @throws InvalidCredentialsException se a autenticacao falhar
     */
    public boolean atualizarEmail(String emailAtual, String senha, String novoEmail) throws NewCredentialEqualsPreviousException,
            InvalidCredentialsException {
        if (emailAtual.equals(this.email) && senha.equals(this.senha)) {
            if (!novoEmail.equals(this.email)) {
                this.setEmail(novoEmail);
                return true;
            }
            else {
                throw new NewCredentialEqualsPreviousException(this, "email: " + this.email,
                        "O novo email deve ser diferente do antigo.");
            }
        }
        else {
            throw new InvalidCredentialsException("Senha ou email inválido", this);
        }
    }

    /**
     * Tenta atualizar a senha do usuario mediante autenticacao.
     *
     * @param email o email do usuario.
     * @param senhaAtual a senha atual do usuario.
     * @param novaSenha a nova senha desejada.
     * @return true se a operacao for bem-sucedida.
     * @throws InvalidCredentialsException se a autenticacao falhar.
     * @throws NewCredentialEqualsPreviousException se a nova senha for igual a anterior.
     */
    public boolean atualizarSenha(String email, String senhaAtual, String novaSenha) throws InvalidCredentialsException,
            NewCredentialEqualsPreviousException{
        if (email.equals(this.email) && senhaAtual.equals(this.senha)) {
            if (!novaSenha.equals(this.senha)) {
                this.setSenha(novaSenha);
                return true;
            }
            else {
                throw new NewCredentialEqualsPreviousException(this, "senha",
                        "A nova senha deve ser diferente da atual.");
            }
        }
        else {
            throw new InvalidCredentialsException("Senha ou email invalido.", this);
        }
    }

    /**
     * Tenta atualizar o celular do usuario mediante autenticacao.
     *
     * @param email o email do usuario.
     * @param senha a senha d usuario.
     * @param novoCelular o novo celular desejado.
     * @return true se a operacao for bem-sucedida.
     * @throws InvalidCredentialsException se a autenticacao falhar.
     * @throws NewCredentialEqualsPreviousException se o novo celular for igual ao anterior.
     */
    public boolean atualizarCelular(String email, String senha, String novoCelular) throws InvalidCredentialsException,
            NewCredentialEqualsPreviousException {
        if (email.equals(this.email) && senha.equals(this.senha)) {
            if (!novoCelular.equals(this.celular)) {
                this.setCelular(novoCelular);
                return true;
            }
            else {
                throw new NewCredentialEqualsPreviousException(this, "Celular: " + this.celular,
                        "O novo celular deve ser diferente do antigo.");
            }
        }
        else {
            throw new InvalidCredentialsException("Email ou senha invalido.", this);
        }
    }

    /**
     * Tenta atualizar o endereco do usuario mediante autenticacao.
     *
     * @param email o email do usuario.
     * @param senha a senha do usuario.
     * @param novoEndereco o novo endereco desejado.
     * @return true se a operacao for bem sucedida.
     * @throws InvalidCredentialsException se a autenticacao falhar.
     * @throws NewCredentialEqualsPreviousException se o novo endereco for igual ao anterior.
     */
    public boolean atualizarEndereco(String email, String senha, String novoEndereco) throws InvalidCredentialsException,
            NewCredentialEqualsPreviousException{
        if (email.equals(this.email) && senha.equals(this.senha)) {
            if (!novoEndereco.equals(this.endereco)) {
                this.setEndereco(novoEndereco);
                return true;
            }
            else {
                throw new NewCredentialEqualsPreviousException(this, "Endereco: " + this.endereco,
                        "O novo endereco deve ser diferente do anterior.");
            }
        }
        else {
            throw new InvalidCredentialsException("Email ou senha invalido.", this);
        }
    }
}
