package tcatelie.microservice.auth.strategy;

import tcatelie.microservice.auth.model.Pedido;

public interface Pagavel {
    Pedido pagar(Pedido pedido);
}
