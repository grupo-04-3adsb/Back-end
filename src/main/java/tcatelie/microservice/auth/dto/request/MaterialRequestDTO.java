package tcatelie.microservice.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialRequestDTO {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    @Positive
    private Double preco;
}
