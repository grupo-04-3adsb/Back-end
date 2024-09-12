package tcatelie.microservice.auth.mapper;

import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.model.Endereco;
import tcatelie.microservice.auth.util.converters.LogradouroConverter;

@Component
public class EnderecoMapper {

    private final LogradouroConverter logradouroConverter = new LogradouroConverter();

    public Endereco toEntity(EnderecoRequestDTO dto) throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep().replaceAll("\\D+", ""));
        endereco.setPais(dto.getPais());
        endereco.setNomeContato(dto.getNomeContato());
        endereco.setTelefoneContato(dto.getTelefoneContato().replaceAll("\\D+", ""));
        endereco.setEmailContato(dto.getEmailContato());
        endereco.setInstrucoesEntrega(dto.getInstrucoesEntrega());
        endereco.setEnderecoPadrao(dto.isEnderecoPadrao());
        endereco.setTipo(dto.getTipo() != null ? logradouroConverter.convertToEntityAttribute(dto.getTipo()) : null);

        return endereco;
    }

    public EnderecoResponseDTO toDto(Endereco endereco) throws Exception {
        EnderecoResponseDTO dto = new EnderecoResponseDTO();
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        dto.setComplemento(endereco.getComplemento());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setEstado(endereco.getEstado());
        dto.setCep(endereco.getCep());
        dto.setPais(endereco.getPais());
        dto.setNomeContato(endereco.getNomeContato());
        dto.setTelefoneContato(endereco.getTelefoneContato());
        dto.setEmailContato(endereco.getEmailContato());
        dto.setInstrucoesEntrega(endereco.getInstrucoesEntrega());
        dto.setEnderecoPadrao(endereco.isEnderecoPadrao());
        dto.setTipo(endereco.getTipo() != null ? logradouroConverter.convertToDatabaseColumn(endereco.getTipo()) : null);
        return dto;
    }


    public String formatarCep(String numero) {
        String numeroStr = String.format("%08d", numero);
        return numeroStr.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
    }
}
