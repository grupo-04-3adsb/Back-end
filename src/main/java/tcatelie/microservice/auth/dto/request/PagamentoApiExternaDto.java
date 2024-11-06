package tcatelie.microservice.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO que representa um pagamento de uma API externa.")
public class PagamentoApiExternaDto {

    @Schema(description = "Nome do pagamento", example = "Cartão de crédito")
    @JsonProperty("name")
    private String nome;

    @Schema(description = "ID do pagamento", example = "1")
    @JsonProperty("payment_type_id")
    private String tipoPagamento;

    @Schema(description = "Status do pagamento", example = "approved")
    @JsonProperty("status")
    private String status;

}
