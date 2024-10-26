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

    @Operation(summary = "Criar preferência de pagamento",
            description = "Cria uma preferência de pagamento no MercadoPago",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Preferência de pagamento criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro ao criar preferência de pagamento")
            }
    )
    @PostMapping("/criar-preferencia")
    public ResponseEntity<String> criarPreferencia() {
        try {
            String preferenceId = mercadoPagoService.criarPreferencia();
            return ResponseEntity.status(HttpStatus.OK).body(preferenceId);
        } catch (MPException | MPApiException e) {
            logger.error("Erro ao criar preferência: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar preferência de pagamento: " + e.getMessage());
        }
    }
}
