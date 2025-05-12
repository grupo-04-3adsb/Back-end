package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.response.material_produto.MaterialProdutoResumidoResponseDTO;
import tcatelie.microservice.auth.model.MaterialProduto;

public class MaterialProdutoMapper {

    public static MaterialProdutoResumidoResponseDTO toMaterialProdutoResponseDTO(MaterialProduto materialProduto) {
        return MaterialProdutoResumidoResponseDTO.builder()
                .idMaterial(materialProduto.getMaterial().getIdMaterial())
                .nomeMaterial(materialProduto.getMaterial().getNomeMaterial())
                .qtdMaterial(materialProduto.getQtdMaterialNecessario())
                .build();
    }

}
