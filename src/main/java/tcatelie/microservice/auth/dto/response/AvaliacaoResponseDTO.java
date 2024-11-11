package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO que representa uma avaliação de um produto.")
public class AvaliacaoResponseDTO {

    @Schema(description = "ID da avaliação", example = "1")
    private Integer id;

    @Schema(description = "Título da avaliação", example = "Ótimo produto!")
    private String titulo;

    @Schema(description = "Descrição detalhada da avaliação", example = "Produto de excelente qualidade, superou minhas expectativas.")
    private String descricao;

    @Schema(description = "Nota dada pelo usuário para o produto (1 a 5)", example = "5")
    private Integer nota;

    @Schema(description = "Indica se a avaliação foi aprovada", example = "true")
    private Boolean aprovada;

    @Schema(description = "ID do usuário que fez a avaliação", example = "123")
    private Integer usuarioId;

    @Schema(description = "Nome do usuário que fez a avaliação", example = "João Silva")
    private String nomeUsuario;

    @Schema(description = "Data e hora em que a avaliação foi feita", example = "2024-11-08T14:30:00")
    private LocalDateTime dataAvaliacao;

    @Schema(description = "ID do produto que foi avaliado", example = "456")
    private Integer produtoId;

    @Schema(description = "Nome do produto que foi avaliado", example = "Caderno")
    private String nomeProduto;

    @Override
    public String toString() {
        return "AvaliacaoResponseDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", nota=" + nota +
                ", aprovada=" + aprovada +
                ", usuarioId=" + usuarioId +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", dataAvaliacao=" + dataAvaliacao +
                ", produtoId=" + produtoId +
                ", nomeProduto='" + nomeProduto + '\'' +
                '}';
    }
}
