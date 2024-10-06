package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalizacaoResponseDTO {

    @Schema(description = "ID da personalização", example = "1")
    private Integer idPersonalizacao;

    @Schema(description = "Nome da personalização", example = "Cor")
    private String nomePersonalizacao;

    @Schema(description = "Tipo da personalização", example = "Seleção")
    private String tipoPersonalizacao;

    @Schema(description = "Data e hora de criação da personalização", example = "2024-10-06T10:15:30")
    private LocalDateTime dthrCriacao;

    @Schema(description = "Data e hora da última atualização da personalização", example = "2024-10-06T10:15:30")
    private LocalDateTime dthrAtualizacao;

    @Schema(description = "Lista de opções de personalização")
    private List<OpcaoPersonalizacaoResponseDTO> opcoes;
}
