import java.time.LocalDate;

import controllers.UsuarioControllerImpl;
import models.usuario.Cliente;

public class main {
    public static void main(String[] args) {

        Cliente cliente = new Cliente(0, "Fernanda Santos", LocalDate.now(), "36589466", "1586452", "f243385@dac.unicamp", "123456", "59595858", "284952628", "sdfsdfsdf", "9589859", LocalDate.now(), 123, "Teste", LocalDate.now());
        new UsuarioControllerImpl().cadastrarUsuario(cliente);
    }
}
