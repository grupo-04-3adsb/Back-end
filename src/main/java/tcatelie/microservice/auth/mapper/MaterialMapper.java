package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.request.MaterialRequestDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialDetalhadoResponseDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialResponseDTO;
import tcatelie.microservice.auth.model.Material;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MaterialMapper {

    public static Material toEntity(MaterialRequestDTO dto){

        if(dto == null){
            return null;
        }
        return Material.builder()
                .nomeMaterial(dto.getNome())
                .estoque(dto.getQuantidade())
                .precoUnitario(dto.getPreco())
                .descricao(dto.getDescricao())
                .unidadesPorPacote(dto.getUnidadesPorPacote())
                .precoPacote(dto.getPrecoPacote())
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
                .dthrAtualizacao(localDateTimeToString(entidade.getDthrAtualizacao()))
                .dthrCadastro(localDateTimeToString(entidade.getDthrCadastro()))
                .descricao(entidade.getDescricao())
                .unidadesPorPacote(entidade.getUnidadesPorPacote())
                .precoPacote(entidade.getPrecoPacote())
                .build();
    }

    private static String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
        return dateTime.format(formatter);
    }

}
