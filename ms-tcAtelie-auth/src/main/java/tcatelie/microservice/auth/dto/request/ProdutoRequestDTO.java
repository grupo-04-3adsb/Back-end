package tcatelie.microservice.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoRequestDTO {

    private String nome;
    private Double preco;
    private String descricao;
    private String material;
    private String dimensao;
    private String url_produto;
}
