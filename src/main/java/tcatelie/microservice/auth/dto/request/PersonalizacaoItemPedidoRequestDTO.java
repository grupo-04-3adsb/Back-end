package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Item de personalização")
public class PersonalizacaoItemPedidoRequestDTO {

    @Schema(description = "Identificador da personalização do item do pedido")
    private Integer id;

    @Schema(description = "Descrição da personalização")
    private String descricaoPersonalizacao;

    @Schema(description = "Valor da personalização")
    private Double valorPersonalizacao;

    @Schema(description = "Identificador do item do pedido")
    private Integer fkItemPedido;

    @Schema(description = "Identificador da personalização")
    private Integer fkPersonalizacao;

    @Schema(description = "Identificador da opção de personalização")
    private Integer fkOpcaoPersonalizacao;
}
