package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.model.Endereco;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

	EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

	@Mapping(target = "dthrCadastro", ignore = true)
	@Mapping(target = "dthrAtualizacao", ignore = true)
	Endereco toEndereco(EnderecoRequestDTO enderecoRequestDTO);

	EnderecoResponseDTO toEnderecoResponseDTO(Endereco endereco);

}
