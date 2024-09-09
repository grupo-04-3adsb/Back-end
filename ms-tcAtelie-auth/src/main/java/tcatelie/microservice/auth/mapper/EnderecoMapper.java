package tcatelie.microservice.auth.mapper;

import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.model.Endereco;
import tcatelie.microservice.auth.enums.Logradouro;
import tcatelie.microservice.auth.security.AESUtil;
import tcatelie.microservice.auth.util.converters.LogradouroConverter;

@Component
public class EnderecoMapper {

    private final LogradouroConverter logradouroConverter = new LogradouroConverter();

    public Endereco toEntity(EnderecoRequestDTO dto) throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua(dto.getRua() != null ? AESUtil.encrypt(dto.getRua()) : null);
        endereco.setNumero(dto.getNumero() != null ? AESUtil.encrypt(dto.getNumero()) : null);
        endereco.setComplemento(dto.getComplemento() != null ? AESUtil.encrypt(dto.getComplemento()) : null);
        endereco.setBairro(dto.getBairro() != null ? AESUtil.encrypt(dto.getBairro()) : null);
        endereco.setCidade(dto.getCidade() != null ? AESUtil.encrypt(dto.getCidade()) : null);
        endereco.setEstado(dto.getEstado() != null ? AESUtil.encrypt(dto.getEstado()) : null);
        endereco.setCep(dto.getCep() != null ? AESUtil.encrypt(dto.getCep().replaceAll("\\D+", "")) : null);
        endereco.setPais(dto.getPais() != null ? AESUtil.encrypt(dto.getPais()) : null);
        endereco.setNomeContato(dto.getNomeContato() != null ? AESUtil.encrypt(dto.getNomeContato()) : null);
        endereco.setTelefoneContato(dto.getTelefoneContato() != null ? AESUtil.encrypt(dto.getTelefoneContato().replaceAll("\\D+", "")) : null);
        endereco.setEmailContato(dto.getEmailContato() != null ? AESUtil.encrypt(dto.getEmailContato()) : null);
        endereco.setInstrucoesEntrega(dto.getInstrucoesEntrega() != null ? AESUtil.encrypt(dto.getInstrucoesEntrega()) : null);
        endereco.setEnderecoPadrao(dto.isEnderecoPadrao());
        endereco.setTipo(dto.getTipo() != null ? logradouroConverter.convertToEntityAttribute(dto.getTipo()) : null);

        return endereco;
    }

    public EnderecoResponseDTO toDto(Endereco endereco) throws Exception {
        EnderecoResponseDTO dto = new EnderecoResponseDTO();
        dto.setRua(endereco.getRua() != null ? AESUtil.decrypt(endereco.getRua()) : null);
        dto.setNumero(endereco.getNumero() != null ? AESUtil.decrypt(endereco.getNumero()) : null);
        dto.setComplemento(endereco.getComplemento() != null ? AESUtil.decrypt(endereco.getComplemento()) : null);
        dto.setBairro(endereco.getBairro() != null ? AESUtil.decrypt(endereco.getBairro()) : null);
        dto.setCidade(endereco.getCidade() != null ? AESUtil.decrypt(endereco.getCidade()) : null);
        dto.setEstado(endereco.getEstado() != null ? AESUtil.decrypt(endereco.getEstado()) : null);
        dto.setCep(endereco.getCep() != null ? AESUtil.decrypt(endereco.getCep()) : null);
        dto.setPais(endereco.getPais() != null ? AESUtil.decrypt(endereco.getPais()) : null);
        dto.setNomeContato(endereco.getNomeContato() != null ? AESUtil.decrypt(endereco.getNomeContato()) : null);
        dto.setTelefoneContato(endereco.getTelefoneContato() != null ? AESUtil.decrypt(endereco.getTelefoneContato()) : null);
        dto.setEmailContato(endereco.getEmailContato() != null ? AESUtil.decrypt(endereco.getEmailContato()) : null);
        dto.setInstrucoesEntrega(endereco.getInstrucoesEntrega() != null ? AESUtil.decrypt(endereco.getInstrucoesEntrega()) : null);
        dto.setEnderecoPadrao(endereco.isEnderecoPadrao());
        dto.setTipo(endereco.getTipo() != null ? logradouroConverter.convertToDatabaseColumn(endereco.getTipo()) : null);
        return dto;
    }

    public String formatarCep(String numero) {
        String numeroStr = String.format("%08d", numero);
        return numeroStr.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
    }
}
