package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request DTO para o cadastro de uma nova avaliação de produto")
public class AvaliacaoRequestDTO {

    @Schema(description = "Título da avaliação", example = "Ótimo produto!")
    @NotBlank(message = "O título da avaliação é obrigatório")
    private String titulo;

    @Schema(description = "Descrição detalhada da avaliação", example = "Produto de excelente qualidade, superou minhas expectativas.")
    @NotBlank(message = "A descrição da avaliação é obrigatória")
    private String descricao;

    @Schema(description = "Nota dada pelo usuário para o produto (1 a 5)", example = "5")
    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota deve ser pelo menos 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Integer nota;

    @Schema(description = "Indica se a avaliação foi aprovada", example = "true")
    private Boolean aprovada;

    @Schema(description = "ID do usuário que fez a avaliação", example = "123")
    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer usuarioId;

    @Schema(description = "ID do produto que está sendo avaliado", example = "456")
    @NotNull(message = "O ID do produto é obrigatório")
    private Integer produtoId;

    @Override
    public String toString() {
        return "AvaliacaoRequestDTO{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", nota=" + nota +
                ", aprovada=" + aprovada +
                ", usuarioId=" + usuarioId +
                ", produtoId=" + produtoId +
                '}';
    }
}
