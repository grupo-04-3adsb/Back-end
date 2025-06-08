package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.model.Usuario;

public class UsuarioMapperManual {

    public static UsuarioResponseDTO toUsuarioResponseDTO(Usuario entity) {
        return UsuarioResponseDTO.builder()
                .idUsuario(entity.getIdUsuario())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .numeroTelefone(entity.getTelefone())
                .cpf(entity.getCpf())
                .cargo(entity.getRole().toString())
                .imgUrl(entity.getUrlImgUsuario())
                .build();
    }

}
