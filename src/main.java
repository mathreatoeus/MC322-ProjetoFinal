import java.time.LocalDate;

import controllers.UsuarioControllerImpl;
import models.usuario.Cliente;
import models.usuario.Funcionario;


public class main {
    public static void main(String[] args) {

        Cliente cliente = new Cliente(0, "Fernanda Santos", LocalDate.now(), "1586452",
                "f243385@dac.unicamp", "123456", "59595858", "sdfsdfsdf", "9589859", LocalDate.now(), 123,
                "Teste", LocalDate.now());
        new UsuarioControllerImpl().cadastrarCliente(cliente);

        Funcionario funcionario = new Funcionario(0, "Felipe Teste", LocalDate.now(), 
        "48481588", "sdjhfufyhusidf@dac", "95959", "1998370035",  "sdhfushdfusf", Funcionario.Cargo.CORRETOR);
        new UsuarioControllerImpl().cadastrarFuncionario(funcionario);

        /* teste de credenciais */

        new UsuarioControllerImpl().credenciaisCliente("f243385@dac.unicamp", "123456");
    }
}
