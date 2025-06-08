package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.response.produto.ProdutoResumidoResponseDTO;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.util.CreateImageUrl;
import tcatelie.microservice.auth.util.converters.CreateImageUrlManual;

public class ProdutoMapperManual {

    public static ProdutoResumidoResponseDTO toProdutoResumidoResponseDTO(Produto produto) {

        CreateImageUrlManual createImageUrl = new CreateImageUrlManual();
        String urlImagem = createImageUrl.getCompleteImageUrl(produto.getUrlImagemPrincipal());

        return ProdutoResumidoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .imagem(urlImagem)
                .categoria(CategoriaMapperManual.toCategoriaResumidaDTO(produto.getCategoria()))
                .dimensao(produto.getDimensao())
                .sku(produto.getSku())
                .urlImgPrincipal(produto.getUrlImagemPrincipal())
                .personalizavel(produto.isPersonalizavel())
                .personalizacaoObrigatoria(produto.isPersonalizacaoObrigatoria())
                .produtoAtivo(produto.getProdutoAtivo())
                .subcategoria(SubcategoriaMapper.INSTANCE.toSubcategoriaResponse(produto.getSubcategoria()))
                .materiais(produto.getMateriaisProduto() != null ? produto.getMateriaisProduto().stream()
                        .map(MaterialProdutoMapper::toMaterialProdutoResponseDTO)
                        .toList() : null)
                .build();
    }

}
