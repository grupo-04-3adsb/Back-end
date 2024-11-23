package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ShippingCompanyResponseDTO", description = "Empresa de transporte")
public class ShippingCompanyResponseDTO {

    @Schema(name = "id", description = "Identificador da empresa de transporte")
    private int id;
    @Schema(name = "name", description = "Nome da empresa de transporte")
    private String name;
    @Schema(name = "slug", description = "Slug da empresa de transporte")
    private String picture;
}
