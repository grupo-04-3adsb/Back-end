package tcatelie.microservice.auth.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PedidoRepository;
import tcatelie.microservice.auth.service.MercadoPagoService;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mercadopago")
@RequiredArgsConstructor
@Tag(name = "MercadoPago API", description = "MercadoPago API")
public class MercadoPagoApiController {

    private final Logger logger = LoggerFactory.getLogger(MercadoPagoApiController.class);
    private final PedidoRepository pedidoRepository;

    @Resource
    private MercadoPagoService mercadoPagoService;

    @Operation(summary = "Criar PIX",
            description = "Cria um PIX no MercadoPago",
            responses = {
                    @ApiResponse(responseCode = "200", description = "PIX criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro ao criar PIX")
            }
    )
    @PostMapping("/pix/{idPedido}")
    public ResponseEntity<String> criarPix(@PathVariable Integer idPedido) {
        try {
            String preferenceId = mercadoPagoService.criarPagamento(idPedido);
            return ResponseEntity.status(HttpStatus.OK).body(preferenceId);
        } catch (MPException | MPApiException e) {
            logger.error("Erro ao criar pagamento pix: {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar preferência de pagamento: " + e.getMessage());
        }
    }

    @Operation(summary = "Processar pagamento",
            description = "Processa o pagamento de um pedido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento processado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro ao processar pagamento")
            }
    )
    @PostMapping("/process_payment/{idPedido}")
    public ResponseEntity<String> processPayment(@PathVariable Integer idPedido) {
        try {
            String preferenceId = mercadoPagoService.criarPagamentoPix(idPedido);
            return ResponseEntity.status(HttpStatus.OK).body(preferenceId);
        } catch (MPException | MPApiException e) {
            logger.error("Erro ao criar pagamento pix: {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar preferência de pagamento: " + e.getMessage());
        }
    }

    @PostMapping("/payment")
    public ResponseEntity handlePaymentNotification(@RequestBody Map<String, Object> payload) {
        logger.info("Recebendo notificação de pagamento: {}", payload);

        try {
            String paymentId = (String) payload.get("id");
            String status = (String) payload.get("status");

            if ("approved".equals(status)) {
                Integer pedidoId = Integer.valueOf((String) payload.get("external_reference"));

                Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);
                if (pedidoOptional.isPresent()) {
                    Pedido pedido = pedidoOptional.get();
                    pedido.setStatus(StatusPedido.PENDENTE);
                    pedidoRepository.save(pedido);
                    logger.info("Pedido {} atualizado como Pago", pedidoId);
                } else {
                    logger.warn("Pedido não encontrado para o ID {}", pedidoId);
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao processar notificação de pagamento", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
