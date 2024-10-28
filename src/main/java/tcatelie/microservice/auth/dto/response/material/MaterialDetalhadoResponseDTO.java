package tcatelie.microservice.auth.dto.response.material;

import lombok.Builder;
import lombok.Data;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.model.MaterialProduto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MaterialDetalhadoResponseDTO {

    private Integer id;

    private String nome;

    private Integer quantidade;

    private Double preco;

}
