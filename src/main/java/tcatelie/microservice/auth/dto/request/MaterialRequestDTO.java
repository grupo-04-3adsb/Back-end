package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO para a criação de um novo material")
public class MaterialRequestDTO {

    @Schema(description = "Identificador do material", example = "1")
    private Integer id;

    @NotBlank
    @Schema(description = "Nome do material", example = "Tecido")
    private String nome;

    @Schema(description = "Quantidade em estoque", example = "100")
    private Integer quantidade;

    @Schema(description = "Descrição do material", example = "Tecido de algodão")
    private String descricao;

    @Schema(description = "Preço do pacote", example = "100.00")
    private Double precoPacote;

    @Schema(description = "Unidades por pacote", example = "10")
    private Integer unidadesPorPacote;

    @NotNull
    @Positive
    @Schema(description = "Preço do material", example = "10.00")
    private Double preco;
}
