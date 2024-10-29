package tcatelie.microservice.auth.dto.response.material;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialResponseDTO {

    private int id;
    private String nome;

}
