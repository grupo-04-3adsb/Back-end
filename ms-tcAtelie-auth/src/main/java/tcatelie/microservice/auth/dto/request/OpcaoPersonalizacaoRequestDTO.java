package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO para a opção de personalização do produto.")
public class OpcaoPersonalizacaoRequestDTO {

    @Schema(description = "ID da opção de personalização", example = "1")
    @NotNull(message = "O ID da opção é obrigatório.")
    private Integer idOpcao;

    @Schema(description = "Nome da opção de personalização", example = "Azul")
    @NotBlank(message = "O nome da opção é obrigatório.")
    private String nomeOpcao;

    @Schema(description = "Descrição do produto para essa opção", example = "Produto na cor azul.")
    @NotBlank(message = "A descrição da opção é obrigatória.")
    private String descricaoProduto;

    @Schema(description = "ID da imagem no drive ao qual a opcao pertence", example = "1")
    private Integer idDrive;

    @Schema(description = "URL da imagem que representa essa opção", example = "https://drive.google.com/file/d/abc123456")
    @NotBlank(message = "A URL da imagem da opção é obrigatória.")
    private String urlImagemOpcao;

    @Schema(description = "Acrescimo no preço devido à escolha dessa opção", example = "10.00")
    @Positive(message = "O acréscimo deve ser um valor positivo.")
    private Double acrescimoOpcao;
}