package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.request.MaterialRequestDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialDetalhadoResponseDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialResponseDTO;
import tcatelie.microservice.auth.model.Material;

public class MaterialMapper {

    public static Material toEntity(MaterialRequestDTO dto){

        if(dto == null){
            return null;
        }
        return Material.builder()
                .nomeMaterial(dto.getNome())
                .estoque(dto.getQuantidade())
                .precoUnitario(dto.getPreco())
                .build();
    }

    public static MaterialResponseDTO toMaterialResponseDTO(Material entidade){

        if(entidade==null){
            return null;
        }
        return MaterialResponseDTO.builder()
                .id(entidade.getIdMaterial())
                .nome(entidade.getNomeMaterial())
                .build();
    }

    public static MaterialDetalhadoResponseDTO toMaterialDetalhadoResponseDTO(Material entidade) {
        if(entidade==null){
            return null;
        }
        return MaterialDetalhadoResponseDTO.builder()
                .id(entidade.getIdMaterial())
                .nome(entidade.getNomeMaterial())
                .quantidade(entidade.getEstoque())
                .preco(entidade.getPrecoUnitario())
                .build();
    }

}
