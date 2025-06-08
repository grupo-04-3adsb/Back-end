package tcatelie.microservice.auth.dto.response.produto;

import lombok.Builder;
import tcatelie.microservice.auth.dto.response.MaterialProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.dto.response.categoria.CategoriaResumidaDTO;
import tcatelie.microservice.auth.dto.response.material_produto.MaterialProdutoResumidoResponseDTO;

import java.util.List;

@Builder
public record ProdutoResumidoResponseDTO(
        Integer id,
        String nome,
        String descricao,
        Double preco,
        String imagem,
        String dimensao,
        String sku,
        String urlImgPrincipal,
        Boolean personalizavel,
        Boolean personalizacaoObrigatoria,
        Boolean produtoAtivo,
        CategoriaResumidaDTO categoria,
        SubcategoriaResponseDTO subcategoria,
        List<MaterialProdutoResumidoResponseDTO> materiais
) {
}
