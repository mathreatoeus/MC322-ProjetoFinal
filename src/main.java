import java.time.LocalDate;

import controllers.SubjectObserver;

import controllers.ObserverUser;
import controllers.UsuarioControllerImpl;
import models.usuario.Cliente;
import models.usuario.Funcionario;

public class main {
    public static void main(String[] args) {

        Cliente cliente = new Cliente(0, "Fernanda Santos", LocalDate.now(), "1586452",
                "f243385@dac.unicamp.br", "123456", "59595858", "sdfsdfsdf", "9589859", LocalDate.now(), 123,
                "Teste", LocalDate.now());
        new UsuarioControllerImpl().cadastrarCliente(cliente);

        Funcionario funcionario = new Funcionario(0, "Felipe Teste", LocalDate.now(),
                "48481588", "f243385@dac.unicamp.br", "95959", "1998370035", "sdhfushdfusf", Funcionario.Cargo.CORRETOR);
        new UsuarioControllerImpl().cadastrarFuncionario(funcionario);

        /* teste de credenciais */
        new UsuarioControllerImpl().credenciaisCliente("f243385@dac.unicamp", "123456");

        /* ->TESTE<- DA IMPLEMENTAÇÃO DO OBSERVER */

        /* Criando nova classe de Subject */
        SubjectObserver subject = new SubjectObserver();

        /* Criando usuários (Observers) */
        ObserverUser user1 = new ObserverUser(cliente.getNome());
        ObserverUser user2 = new ObserverUser(funcionario.getNome());

        /* Associando os usuários ao assunto para receber notificações */
        subject.attach(user1);
        subject.attach(user2);

        /* Simulação de atualização de mensagem no assunto (Subject) */
        subject.setMessage("Oferta especial: Desconto de 20% em todos os pacotes!");

    }
}
