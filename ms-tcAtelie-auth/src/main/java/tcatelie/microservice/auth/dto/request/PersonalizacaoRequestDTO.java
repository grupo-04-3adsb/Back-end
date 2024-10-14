package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema(description = "Request DTO para a personalização do produto.")
@AllArgsConstructor
@NoArgsConstructor
public class PersonalizacaoRequestDTO {

    @Schema(description = "ID da personalização", example = "1")
    @NotNull
    private Integer idPersonalizacao;

    @Schema(description = "Nome da personalização", example = "Cor")
    @NotBlank(message = "O nome da personalização é obrigatório.")
    private String nomePersonalizacao;

    @Schema(description = "Tipo de personalização", example = "Seleção")
    @NotBlank(message = "O tipo de personalização é obrigatório.")
    private String tipoPersonalizacao;

    @Schema(description = "Lista de opções de personalização.")
    @NotEmpty(message = "Deve haver pelo menos uma opção de personalização.")
    private List<OpcaoPersonalizacaoRequestDTO> opcoes;
}
