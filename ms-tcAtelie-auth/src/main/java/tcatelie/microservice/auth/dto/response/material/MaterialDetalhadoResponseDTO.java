package tcatelie.microservice.auth.dto.response.material;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialDetalhadoResponseDTO {

    private Integer id;

    private String nome;

    private Integer quantidade;

    private Double preco;

}
