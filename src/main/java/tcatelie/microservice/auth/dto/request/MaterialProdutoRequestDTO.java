package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO que representa os materiais necessários para a produção de um produto")
public class MaterialProdutoRequestDTO {

    @Schema(description = "Id do material", example = "1")
    @NotNull(message = "O id do material é obrigatório")
    public Integer idMaterial;

    @Schema(description = "Quantidade de material necessária para a produção do produto", example = "5")
    @Positive(message = "A quantidade de material necessária deve ser maior que zero")
    @NotNull(message = "A quantidade de material necessária não pode ser nula")
    public Integer qtdMaterialNecessaria;

    @Schema(description = "Quantidade atual em estoque desse material", example = "100")
    @Positive(message = "A quantidade em estoque deve ser maior que zero")
    @NotNull(message = "A quantidade em estoque não pode ser nula")
    public Integer qtdEstoque;

    @Schema(description = "Preço unitário do material", example = "2.50")
    @Positive(message = "O preço unitário deve ser maior que zero")
    @NotNull(message = "O preço unitário não pode ser nulo")
    public Double precoUnitario;

    public MaterialProdutoRequestDTO(Integer idMaterial, Integer qtdMaterialNecessario) {
        this.idMaterial = idMaterial;
        this.qtdMaterialNecessaria = qtdMaterialNecessario;
    }
}
