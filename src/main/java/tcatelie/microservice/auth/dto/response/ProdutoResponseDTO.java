package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO que representa um produto.")
public class ProdutoResponseDTO {

    @Schema(description = "ID do produto", example = "1")
    private Integer id;

    @Schema(description = "Nome do produto", example = "Caderno")
    private String nome;

    @Schema(description = "Preço do produto", example = "30.00")
    private Double preco;

    @Schema(description = "Descrição do produto", example = "Caderno de capa dura")
    private String descricao;

    @Schema(description = "SKU do produto", example = "123456")
    private String sku;

    @Schema(description = "Dimensão do produto", example = "10x15")
    private String dimensao;

    @Schema(description = "URL do produto", example = "https://drive.google.com/...")
    private String urlProduto;

    @Schema(description = "ID da imagem do produto no Google Drive", example = "abc123456")
    private String idImgDrive;

    @Schema(description = "Data e hora de criação do produto", example = "2024-10-06T10:15:30")
    private LocalDateTime dthrCriacao;

    @Schema(description = "Data e hora da última atualização do produto", example = "2024-10-06T10:15:30")
    private LocalDateTime dthrAtualizacao;

    @Schema(description = "Lista de imagens adicionais do produto")
    private List<ImagemProdutoResponseDTO> imagensAdicionais;

    @Schema(description = "Preço de produção do produto", example = "15.00")
    private Double precoProducao;

    @Schema(description = "Desconto do produto", example = "5.00")
    private Double desconto;

    @Schema(description = "Margem de lucro do produto", example = "10.00")
    private Double margemLucro;

    @Schema(description = "Quantidade em estoque do produto", example = "100")
    private Boolean isPersonalizavel;

    @Schema(description = "Quantidade em estoque do produto", example = "100")
    private Boolean isPersonalizacaoObrigatoria;

    @Schema(description = "Quantidade em estoque do produto", example = "100")
    private CategoriaResponseDTO categoria;

    @Schema(description = "Quantidade em estoque do produto", example = "100")
    private Boolean produtoAtivo;

    @Schema(description = "Quantidade em estoque do produto", example = "100")
    private SubcategoriaResponseDTO subcategoria;

    @Schema(description = "Quantidade em estoque do produto", example = "100")
    private List<MaterialProdutoResponseDTO> materiais;

    @Schema(description = "Quantidade de personalizações do produto", example = "100")
    private List<PersonalizacaoResponseDTO> personalizacoes;

}
