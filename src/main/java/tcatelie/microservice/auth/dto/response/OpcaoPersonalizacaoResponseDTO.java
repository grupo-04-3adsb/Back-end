package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcaoPersonalizacaoResponseDTO {

    @Schema(description = "ID da opção", example = "1")
    private Integer idOpcao;

    @Schema(description = "Nome da opção", example = "Vermelho")
    private String nomeOpcao;

    @Schema(description = "Descrição da opção", example = "Cor vermelha")
    private String descricaoOpcao;

    @Schema(description = "Acrescimo no preço da opção", example = "15.00")
    private Double acrescimo;

    @Schema(description = "URL da imagem da opção", example = "https://drive.google.com/file/d/abc123456")
    private String urlImagemOpcao;

    @Schema(description = "ID da imagem da opção no Google Drive", example = "abc123456")
    private String idImgDrive;

    @Schema(description = "Data e hora de criação da opção", example = "2024-10-06T10:15:30")
    private LocalDateTime dthrCriacao;

    @Schema(description = "Data e hora da última atualização da opção", example = "2024-10-06T10:15:30")
    private LocalDateTime dthrAtualizacao;
}
