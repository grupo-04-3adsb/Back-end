package tcatelie.microservice.auth.observer;

import tcatelie.microservice.auth.model.Produto;

public interface Observer {

    void update(String message, Produto produto);
}
