package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.model.Usuario;

@Mapper(componentModel = "spring", uses = { EnderecoMapper.class })
public interface UsuarioMapper {

	UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

	@Mappings({
			@Mapping(source = "telefone", target = "numeroTelefone"),
			@Mapping(source = "dthrCadastro", target = "dataCriacao"),
			@Mapping(source = "dthrAtualizacao", target = "dataAtualizacao"),
			@Mapping(source = "role", target = "cargo"), 
			@Mapping(source = "urlImgUsuario", target = "imgUrl"),
			@Mapping(source = "enderecos", target = "enderecos"),
			@Mapping(source = "idUsuario", target= "idUsuario"),
			@Mapping(target = "senha", ignore = true),
			@Mapping(target = "idGoogle", source = "idGoogle")
	})
	UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);

	@Mapping(target = "dthrCadastro", ignore = true)
	@Mapping(target = "dthrAtualizacao", ignore = true)
	@Mapping(source = "imgUrl", target = "urlImgUsuario")
	Usuario toUsuario(RegisterDTO registerDTO);

}