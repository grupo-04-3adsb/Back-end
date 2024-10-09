package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.model.Produto;

@Mapper(componentModel = "spring", uses = {
        ImagensAdicionaisMapper.class,
        PersonalizacaoMapper.class
})
@Component
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    @Mapping(source = "precoVenda", target = "preco")
    @Mapping(source = "urlProduto", target = "urlImagemPrincipal")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "subcategoria", target = "subcategoria")
    @Mapping(source = "isPersonalizavel", target = "personalizavel")
    @Mapping(source = "isPersonalizacaoObrigatoria", target = "personalizacaoObrigatoria")
    @Mapping(source = "desconto", target = "desconto")
    @Mapping(source = "margemLucro", target = "margemLucro")
    Produto toProduto(ProdutoRequestDTO requestDTO);

    @Mapping(source = "urlImagemPrincipal", target = "urlProduto")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "subcategoria", target = "subcategoria")
    @Mapping(source = "personalizavel", target = "isPersonalizavel")
    @Mapping(source = "personalizacaoObrigatoria", target = "isPersonalizacaoObrigatoria")
    @Mapping(source = "margemLucro", target = "margemLucro")
    @Mapping(source = "dthrCadastro", target = "dthrCriacao")
    ProdutoResponseDTO toResponseDTO(Produto produto);
}
