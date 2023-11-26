package controllers;

import java.util.ArrayList;
import java.util.List;

/* Objetivo: implementação do padrão observer para que os usuários sejam notificados sobre ofertas especiais, promoções e novidades */

/* interface do Observer que define o método de atualização */
interface Observer {
    void update(String message);
}

/*
 * Subject (assunto) que mantém uma lista de observadores e notifica sobre as
 * novidades
 */
public class SubjectObserver {
    private List<Observer> observers = new ArrayList<>();
    private String message;

    /* Método para adicionar novos observadores */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /* Método para remover observadores */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /* Método para notificar todos os observadores com uma nova mensagem */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    /* Método para atualizar a mensagem e notificar os observadores */
    public void setMessage(String message) {
        this.message = message;
        notifyObservers();
    }

}
