package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.ImagemProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.ImagemProdutoResponseDTO;
import tcatelie.microservice.auth.model.ImagensProduto;

@Mapper(componentModel = "spring")
@Component
public interface ImagensAdicionaisMapper {

    ImagensProduto toModel(ImagemProdutoRequestDTO requestDTO);

    @Mapping(target = "url", source = "urlImgAdicional")
    @Mapping(target = "idImagemAdicional", source = "idImagem")
    ImagemProdutoResponseDTO toResponse(ImagensProduto imagensProduto);
}
