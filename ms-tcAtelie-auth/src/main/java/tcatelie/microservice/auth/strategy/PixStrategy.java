package tcatelie.microservice.auth.strategy;

import tcatelie.microservice.auth.model.Pedido;

public class PixStrategy implements Pagavel{

    @Override
    public Pedido pagar(Pedido pedido) {
        pedido.setFormaPgto("Pix");
        return pedido;
    }
}
