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
                .nome(dto.getNome())
                .quantidade(dto.getQuantidade())
                .preco(dto.getPreco())
                .build();
    }

    public static MaterialResponseDTO toMaterialResponseDTO(Material entidade){

        if(entidade==null){
            return null;
        }
        return MaterialResponseDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .build();
    }

    public static MaterialDetalhadoResponseDTO toMaterialDetalhadoResponseDTO(Material entidade) {
        if(entidade==null){
            return null;
        }
        return MaterialDetalhadoResponseDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .quantidade(entidade.getQuantidade())
                .preco(entidade.getPreco())
                .build();
    }

}
