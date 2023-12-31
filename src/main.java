import java.time.LocalDate;
import java.time.LocalDateTime;

import controllers.SubjectObserver;

import controllers.ObserverUser;
import controllers.PacoteControllerImpl;
import controllers.UsuarioControllerImpl;
import models.pacote.AluguelCarro;
import models.pacote.Atividade;
import models.pacote.Comentario;
import models.pacote.Hospedagem;
import models.pacote.Local;
import models.pacote.Pacote;
import models.pacote.Pagamento;
import models.pacote.PassagemAerea;
import models.pacote.Reserva;
import models.pacote.Seguro;
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

    
        // 1. Cadastrar Hospedagem
        Hospedagem hospedagem = new Hospedagem(1, "Hotel Exemplo", Hospedagem.TipoHospedagem.HOTEL, Hospedagem.TipoSuite.DUPLA, Hospedagem.TipoCama.CASAL, "Um hotel incrível", "Endereço do Hotel", 1, LocalDateTime.of(2023, 10, 30, 14, 0),LocalDateTime.of(2023, 11, 6, 12, 0), 150.0, 7, true);
        new PacoteControllerImpl().cadastrarHospedagem(hospedagem);

        // 2. Cadastrar Passagem Aérea
        PassagemAerea passagem = new PassagemAerea(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(), 0, "Azul", 45, "Guarulhos", "Aeroporto Bahia", "aaaa", "aaaa");
        new PacoteControllerImpl().cadastrarPassagem(passagem);

        // 3. Cadastrar Aluguel de Carro
        AluguelCarro aluguel = new AluguelCarro(0, 5, "prisma", "conectacar", LocalDateTime.now(), "av a n 123", "av b n124", 5.50, 1);
        new PacoteControllerImpl().cadastrarAluguelCarro(aluguel);

        // 4. Cadastrar Pacote
        Pacote pacote = new Pacote(1, 1, Pacote.Categoria.AVENTURA, passagem.getId(), Pacote.TipoPassagem.AEREA, aluguel.getId(), hospedagem.getIdHospedagem(), 0.00, 1.1);
        new PacoteControllerImpl().cadastrarPacote(pacote);

        // 5. Cadastrar Seguro
        Seguro seguro = new Seguro(1, 14.90, "seguro para 5 dias úteis");
        new PacoteControllerImpl().cadastrarSeguro(seguro);

        // 6. Cadastrar Atividade
        Atividade atividade = new Atividade(1, "passeio 1", 1, "passeio guiado", "av123 n 67", LocalDateTime.now(), LocalDateTime.now(), 45);
        new PacoteControllerImpl().cadastrarAtividade(atividade, pacote);

        // 7. Cadastrar Local
        Local local = new Local(1, "Bahia", Local.Continente.AMERICA_DO_SUL);
        new PacoteControllerImpl().cadastrarLocal(local);

        // 8. Cadastrar Pagamento
        Pagamento pagamento = new Pagamento(1, 1, 333.22, Pagamento.Situacao.PENDENTE, LocalDate.now());
        new PacoteControllerImpl().cadastrarPagamento(pagamento);

        // 9. Cadastrar Reserva
        Reserva reserva = new Reserva(1,1, 1, LocalDateTime.now(), LocalDateTime.now(), 1  , 0.0, 13.3);
        new PacoteControllerImpl().cadastrarReserva(reserva);

        // 10. Cadastrar Comentários
        Comentario comentarioHospedagem = new Comentario(1, 0, "muito bom", LocalDateTime.now());
        new PacoteControllerImpl().cadastrarComentarioHospedagem(comentarioHospedagem, cliente, hospedagem);

        Comentario comentarioLocal = new Comentario(2, 0, "muito bom", LocalDateTime.now());
        new PacoteControllerImpl().cadastrarComentarioLocalizacoes(comentarioLocal, cliente, local);

        Comentario comentarioPacote = new Comentario(3, 0, "muito bom", LocalDateTime.now());
        new PacoteControllerImpl().cadastrarComentarioPacote(comentarioPacote, cliente, pacote);
            
    
    }
}
