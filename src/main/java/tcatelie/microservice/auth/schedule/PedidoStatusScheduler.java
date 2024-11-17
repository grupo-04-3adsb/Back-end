package tcatelie.microservice.auth.schedule;

import com.mercadopago.client.payment.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.service.MercadoPagoService;
import tcatelie.microservice.auth.service.PedidoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoStatusScheduler {

    private final Logger logger = LoggerFactory.getLogger(PedidoStatusScheduler.class);
    private final PedidoService service;
    private final MercadoPagoService mercadoPagoService;

    @Scheduled(fixedRate = 60000)
    public void verificarStatusPagamento() {
        logger.info("Verificando status de pagamento dos pedidos");
        List<Pedido> pedidosPendentes = service.buscarPedidosPorStatus(StatusPedido.PENDENTE_PAGAMENTO);

        for (Pedido pedido : pedidosPendentes) {
            boolean pago = mercadoPagoService.verificarPagamento(pedido.getPaymentId());

            if (pago) {
                logger.info("Pagamento do pedido {} foi confirmado", pedido.getId());
                service.atualizarStatusPedido(pedido, StatusPedido.CONCLUIDO);
            } else {
                service.atualizarStatusPedido(pedido, StatusPedido.PENDENTE_PAGAMENTO);
            }
        }
    }
}
