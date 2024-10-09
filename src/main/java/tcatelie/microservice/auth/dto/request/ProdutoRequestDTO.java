package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO para o cadastro de um novo produto")
public class ProdutoRequestDTO {

    @Schema(description = "Nome do produto", example = "Topo de Bolo Personalizado")
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @Schema(description = "SKU do produto (código único)", example = "SKU123456")
    @NotBlank(message = "O SKU do produto é obrigatório")
    private String sku;

    @Schema(description = "Margem de lucro sobre o custo de produção", example = "10.50")
    @Positive(message = "A margem de lucro deve ser maior que zero")
    @NotNull(message = "A margem de lucro não pode ser nula")
    private Double margemLucro;

    @Schema(description = "Preço de venda do produto", example = "65.00")
    @Positive(message = "O preço de venda deve ser maior que zero")
    @NotNull(message = "O preço de venda não pode ser nulo")
    private Double precoVenda;

    @Schema(description = "Descrição detalhada do produto", example = "Produto artesanal personalizado com nome do cliente")
    @NotBlank(message = "A descrição do produto é obrigatória")
    private String descricao;

    @Schema(description = "Dimensões do produto", example = "20x30x10")
    @NotBlank(message = "As dimensões do produto são obrigatórias")
    private String dimensao;

    @Schema(description = "URL da imagem principal do produto", example = "https://drive.google.com/file/d/abc123456")
    @NotBlank(message = "A URL da imagem do produto é obrigatória")
    private String urlProduto;

    @Schema(description = "Desconto aplicado ao produto", example = "5.00")
    @Positive(message = "O valor do desconto deve ser maior que zero")
    private Double desconto;

    @Schema(description = "Categoria do produto", implementation = CategoriaRequestDTO.class)
    @NotNull(message = "Categoria não pode ser nulo")
    private CategoriaRequestDTO categoria;

    @Schema(description = "Subcategoria do produto", implementation = SubcategoriaRequestDTO.class)
    @NotNull(message = "Subcategoria não pode ser nulo")
    private SubcategoriaRequestDTO subcategoria;

    @Schema(description = "Indica se o produto é personalizável", example = "true")
    @NotNull(message = "O campo personalizável não pode ser nulo")
    private Boolean isPersonalizavel;

    @Schema(description = "Indica se a personalização é obrigatória", example = "true")
    @NotNull(message = "O campo de personalização obrigatória não pode ser nulo")
    private Boolean isPersonalizacaoObrigatoria;

    @Schema(description = "Lista dos materiais utilizados para o produto")
    private List<MaterialProdutoRequestDTO> materiais;

    @Schema(description = "Lista de personalizações do produto")
    private List<PersonalizacaoRequestDTO> personalizacoes;

    @Schema(description = "URLs das imagens adicionais do produto")
    @NotEmpty(message = "O produto precisa ter pelo menos 1 imagem adicional")
    private List<ImagemProdutoRequestDTO> imagensAdicionais;

}
