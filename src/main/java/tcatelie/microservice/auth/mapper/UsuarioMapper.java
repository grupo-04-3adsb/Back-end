package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.response.ResponsavelResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.model.Usuario;

@Mapper(componentModel = "spring", uses = { EnderecoMapper.class })
public interface UsuarioMapper {

	@Mapping(source = "telefone", target = "numeroTelefone")
	@Mapping(source = "dthrCadastro", target = "dataCriacao")
	@Mapping(source = "dthrAtualizacao", target = "dataAtualizacao")
	@Mapping(source = "role", target = "cargo")
	@Mapping(source = "urlImgUsuario", target = "imgUrl")
	@Mapping(source = "enderecos", target = "enderecos")
	@Mapping(target = "idGoogle", source = "idGoogle")
	@Mapping(target = "senha", ignore = true)
	UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);

	@Mapping(target = "dthrCadastro", ignore = true)
	@Mapping(target = "dthrAtualizacao", ignore = true)
	@Mapping(source = "imgUrl", target = "urlImgUsuario")
	Usuario toUsuario(RegisterDTO registerDTO);

    @Mapping(source = "idUsuario", target = "idResponsavel")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telefone", target = "telefone")
    @Mapping(source = "urlImgUsuario", target = "urlImg")
    @Mapping(source = "cpf", target = "cpf")
    @Mapping(source = "genero", target = "genero")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "dthrCadastro", target = "dthrCadastro")
    @Mapping(source = "dthrAtualizacao", target = "dthrAtualizacao")
    ResponsavelResponseDTO toResponsavelResponseDTO(Usuario usuario);

}
