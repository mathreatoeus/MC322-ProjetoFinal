package controllers;

/* Implementação concreta do padrão Observer para os usuários */

public class ObserverUser implements Observer {
    private String username;

    public ObserverUser(String username) {
        this.username = username;
    }

    @Override
    public void update(String message) {
        System.out.println("Usuário " + username + " recebeu a mensagem: " + message);
    }
}
