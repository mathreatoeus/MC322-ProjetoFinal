package models.usuario;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private Cargo cargo;
    // private Localizacao localizacao;

    public Funcionario(int idUsuario, String nome, LocalDate nascimento,String cpf, String email,
            String senha, String celular, String endereco, Cargo cargo) {
        super(idUsuario, nome, nascimento, cpf, email, senha, celular, endereco);
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public enum Cargo {
        CORRETOR, GERENTE, DIRETOR
    }

}
