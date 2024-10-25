package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagemProdutoResponseDTO {
    private Integer idImagemAdicional;
    private String idImgDrive;
    private String url;
}
