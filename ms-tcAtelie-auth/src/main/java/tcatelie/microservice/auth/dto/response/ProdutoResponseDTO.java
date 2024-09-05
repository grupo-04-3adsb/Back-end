package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoResponseDTO {

    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;
    private String material;
    private String dimensao;
    private String url_produto;

}
