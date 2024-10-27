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

    @Mapping(target = "url", source = "urlImgAdicional")
    @Mapping(target = "idImagemAdicional", source = "idImagem")
    @Mapping(source = "idImgDrive", target = "idImgDrive")
    ImagemProdutoResponseDTO toResponse(ImagensProduto imagensProduto);

    @Mapping(target = "urlImgAdicional", source = "url")
    @Mapping(target = "idImagem", source = "id")
    ImagensProduto toModel(ImagemProdutoRequestDTO requestDTO);
}
