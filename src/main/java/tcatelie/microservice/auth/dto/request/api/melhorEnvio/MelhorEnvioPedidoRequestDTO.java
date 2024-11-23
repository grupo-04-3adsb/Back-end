package tcatelie.microservice.auth.dto.request.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "MelhorEnvioPedidoRequestDTO", description = "DTO para requisição de cálculo de frete")
public class MelhorEnvioPedidoRequestDTO {

    @Schema(name = "from", description = "Endereço de origem")
    private FromRequestDTO from;

    @Schema(name = "to", description = "Endereço de destino")
    private ToRequestDTO to;

    @Schema(name = "products", description = "Lista de produtos")
    private List<ProductRequestDTO> products;

    @Schema(name = "options", description = "Opções")
    private OptionsRequestDTO options;

    @Schema(name = "services", description = "Serviços")
    private String services;

}