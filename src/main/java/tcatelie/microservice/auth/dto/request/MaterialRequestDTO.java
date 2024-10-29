package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Request DTO para a criação de um novo material")
public class MaterialRequestDTO {

    @NotBlank
    @Schema(description = "Nome do material", example = "Tecido")
    private String nome;

    @NotNull
    @Positive
    @Schema(description = "Quantidade em estoque", example = "100")
    private Integer quantidade;

    @NotNull
    @Positive
    @Schema(description = "Preço do material", example = "10.00")
    private Double preco;
}
