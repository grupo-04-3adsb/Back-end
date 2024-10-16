package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoResponseDTO {

    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;
    private String sku;
    private String dimensao;
    private String urlProduto;
    private String idImgDrive;
    private LocalDateTime dthrCriacao;
    private LocalDateTime dthrAtualizacao;
    private List<ImagemProdutoResponseDTO> imagensAdicionais;
    private Double precoProducao;
    private Double desconto;
    private Double margemLucro;
    private Boolean isPersonalizavel;
    private Boolean isPersonalizacaoObrigatoria;
    private CategoriaResponseDTO categoria;
    private Boolean produtoAtivo;
    private SubcategoriaResponseDTO subcategoria;
    private List<MaterialProdutoResponseDTO> materiais;
    private List<PersonalizacaoResponseDTO> personalizacoes;

}
