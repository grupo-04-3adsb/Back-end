package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.request.UpdateUserDTO;
import tcatelie.microservice.auth.model.Usuario;

public class AtualizarUsuarioMapper {

    public static Usuario toUsuario(UpdateUserDTO dto, int id){

        if(dto==null){
            return null;
        }

        return Usuario.builder()
                .idUsuario(id)
                .nome(dto.getNome())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .telefone(dto.getTelefone())
                .cpf(dto.getCpf())
                .genero(dto.getGenero())
                .urlImgUsuario(dto.getImgUrl())
                .status(dto.getStatus())
                .role(dto.getRole())
                .build();

    }
}
