package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.response.ResponsavelResponseDTO;
import tcatelie.microservice.auth.model.ResponsavelPedido;

public class ResponsavelPedidoMapper {

    public static ResponsavelResponseDTO toResponsavelResponseDTO(ResponsavelPedido entity) {
        return ResponsavelResponseDTO.builder()
                .idResponsavel(entity.getResponsavel().getIdUsuario())
                .nome(entity.getResponsavel().getNome())
                .email(entity.getResponsavel().getEmail())
                .telefone(entity.getResponsavel().getTelefone())
                .urlImg(entity.getResponsavel().getUrlImgUsuario())
                .role(entity.getResponsavel().getRole().toString())
                .build();
    }
}
