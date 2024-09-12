package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source = "telefone", target = "numeroTelefone")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "dthrCadastro", target = "dataCriacao")
    @Mapping(source = "dthrAtualizacao", target = "dataAtualizacao")
    @Mapping(source = "genero", target = "genero")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "role", target = "cargo")
    @Mapping(source = "urlImgUsuario", target = "imgUrl")
    UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);

    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(target = "dthrCadastro", ignore = true)
    @Mapping(target = "dthrAtualizacao", ignore = true)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "imgUrl", target = "urlImgUsuario")
    Usuario toUsuario(RegisterDTO registerDTO);

}
