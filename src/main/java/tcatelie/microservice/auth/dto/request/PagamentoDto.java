package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO que representa um pagamento.")
public class PagamentoDto {

    @Schema(description = "Nome do pagamento", example = "Cartão de crédito")
    private String nome;

    @Schema(description = "ID do pagamento", example = "1")
    private String tipoPagamento;

    @Schema(description = "Status do pagamento", example = "approved")
    private String status;

}
