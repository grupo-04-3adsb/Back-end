package tcatelie.microservice.auth.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.model.Produto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ProdutoObserver {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message, Produto produto) {
        observers.forEach(observer -> observer.update(message, produto));
    }

    public void cadastrarProduto(String message, Produto produto) {
        notifyObservers(message, produto);
    }

    public void removerProduto(String message, Produto produto) {
        notifyObservers(message, produto);
    }
}
