import controllers.UsuarioControllerImpl;
import models.usuario.Funcionario;
import models.usuario.Usuario;

public class main {
    public static void main(String[] args) {

        Usuario u = new Funcionario(0, "Fernanda Santos", null, null, null, null, null, null, null, null, null);
        new UsuarioControllerImpl().cadastrarUsuario(u);
    }
}
