package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.model.Produto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @Mapping(source = "idImgDrive", target = "idImgDrive")
    @Mapping(source = "produtoAtivo", target = "produtoAtivo")
    @Mapping(source = "dthrCadastro", target = "dthrCriacao", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "dthrAtualizacao", target = "dthrAtualizacao", qualifiedByName = "localDateTimeToString")
    ProdutoResponseDTO toResponseDTO(Produto produto);

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
        return dateTime.format(formatter);
    }
}
