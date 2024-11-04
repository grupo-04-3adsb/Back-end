package tcatelie.microservice.auth.dto.response.material;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.model.MaterialProduto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MaterialDetalhadoResponseDTO {

    @Schema(description = "Identificador do material")
    private Integer id;

    @Schema(description = "Nome do material")
    private String nome;

    @Schema(description = "Quantidade de material")
    private Integer quantidade;

    @Schema(description = "Preço do material")
    private Double preco;

    @Schema(description = "Descrição do material")
    private String descricao;

    @Schema(description = "Produtos relacionados ao material")
    private Integer qtdProdutos;

    @Schema(description = "Data e hora de cadastro do material")
    private String dthrCadastro;

    @Schema(description = "Data e hora de atualização do material")
    private String dthrAtualizacao;
}
