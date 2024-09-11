package tcatelie.microservice.auth.strategy;

import tcatelie.microservice.auth.model.Pedido;

public class DebitoStrategy implements Pagavel{

    @Override
    public Pedido pagar(Pedido pedido) {
        pedido.setFormaPgto("Débito");
        return pedido;
    }
}
