package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {

    @Schema(description = "Id do pedido", example = "1")
    private Integer idPedido;
    @Schema(description = "Nome do cliente", example = "João")
    private String statusPedido;
    @Schema(description = "Status do pedido", example = "Concluído")
    private boolean concluido;
    @Schema(description = "Ids dos responsáveis", example = "[1, 2, 3]")
    private List<Integer> idsResponsaveis;
}
