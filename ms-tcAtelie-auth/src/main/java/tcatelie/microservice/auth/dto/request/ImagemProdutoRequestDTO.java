package tcatelie.microservice.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagemProdutoRequestDTO {
    private Integer id;
    private String url;
}
