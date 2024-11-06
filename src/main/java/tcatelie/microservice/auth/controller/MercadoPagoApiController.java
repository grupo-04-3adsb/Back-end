package tcatelie.microservice.auth.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcatelie.microservice.auth.service.MercadoPagoService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/mercadopago")
@Tag(name = "MercadoPago API", description = "MercadoPago API")
public class MercadoPagoApiController {

    private final Logger logger = LoggerFactory.getLogger(MercadoPagoApiController.class);

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

}
