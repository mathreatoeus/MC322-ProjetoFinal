package models.pacote;

import java.time.LocalDate;

enum Cargo {
    CORRETOR, GERENTE, DIRETOR
}
public class Funcionario extends Usuario{
    private Cargo cargo;
    //private Localizacao localizacao;

    public Funcionario(int idUsuario, String nome, LocalDate nascimento, String rg, String cpf, String email,
            String senha, String celular, String telefoneResidencial, String endereco, Cargo cargo) {
        super(idUsuario, nome, nascimento, rg, cpf, email, senha, celular, telefoneResidencial, endereco);
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
}
