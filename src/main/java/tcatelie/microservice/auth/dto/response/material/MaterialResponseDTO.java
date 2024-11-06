package tcatelie.microservice.auth.dto.response.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialResponseDTO {

    private int id;
    private String nome;

}
