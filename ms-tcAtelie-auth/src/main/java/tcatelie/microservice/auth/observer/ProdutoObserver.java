package tcatelie.microservice.auth.observer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@RequiredArgsConstructor
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

    private void notifyObservers(String message) {
        observers.forEach(observer -> observer.update(message));
    }

    public void cadastrarProduto(String message) {
        notifyObservers(message);
    }

    public void removerProduto(String message) {
        notifyObservers(message);
    }

}
