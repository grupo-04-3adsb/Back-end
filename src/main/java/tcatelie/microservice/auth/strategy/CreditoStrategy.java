package tcatelie.microservice.auth.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcatelie.microservice.auth.model.Pedido;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreditoStrategy implements Pagavel{

    private String numCartao;
    private String validade;
    private String cvv;
    private String nomeTitular;

    @Override
    public Pedido pagar(Pedido pedido) {
        pedido.setFormaPgto("Crédito");
        return pedido;
    }

}
