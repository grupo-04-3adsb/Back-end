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
        endereco.setRua(AESUtil.encrypt(dto.getRua()));
        endereco.setNumero(AESUtil.encrypt(dto.getNumero()));
        endereco.setComplemento(AESUtil.encrypt(dto.getComplemento()));
        endereco.setBairro(AESUtil.encrypt(dto.getBairro()));
        endereco.setCidade(AESUtil.encrypt(dto.getCidade()));
        endereco.setEstado(AESUtil.encrypt(dto.getEstado()));
        endereco.setCep(AESUtil.encrypt(dto.getCep().replaceAll("\\D+", "")));
        endereco.setPais(AESUtil.encrypt(dto.getPais()));
        endereco.setNomeContato(AESUtil.encrypt(dto.getNomeContato()));
        endereco.setTelefoneContato(AESUtil.encrypt(dto.getTelefoneContato().replaceAll("\\D+", "")));
        endereco.setEmailContato(AESUtil.encrypt(dto.getEmailContato()));
        endereco.setInstrucoesEntrega(AESUtil.encrypt(dto.getInstrucoesEntrega()));
        endereco.setEnderecoPadrao(dto.isEnderecoPadrao());

        endereco.setTipo(logradouroConverter.convertToEntityAttribute(dto.getTipo()));

        return endereco;
    }


    public EnderecoResponseDTO toDto(Endereco endereco) throws Exception {
        EnderecoResponseDTO dto = new EnderecoResponseDTO();
        dto.setRua(AESUtil.decrypt(endereco.getRua()));
        dto.setNumero(AESUtil.decrypt(endereco.getNumero()));
        dto.setComplemento(AESUtil.decrypt(endereco.getComplemento()));
        dto.setBairro(AESUtil.decrypt(endereco.getBairro()));
        dto.setCidade(AESUtil.decrypt(endereco.getCidade()));
        dto.setEstado(AESUtil.decrypt(endereco.getEstado()));
        dto.setCep(AESUtil.decrypt(endereco.getCep()));
        dto.setPais(AESUtil.decrypt(endereco.getPais()));
        dto.setNomeContato(AESUtil.decrypt(endereco.getNomeContato()));
        dto.setTelefoneContato(AESUtil.decrypt(endereco.getTelefoneContato()));
        dto.setEmailContato(AESUtil.decrypt(endereco.getEmailContato()));
        dto.setInstrucoesEntrega(AESUtil.decrypt(endereco.getInstrucoesEntrega()));
        dto.setEnderecoPadrao(endereco.isEnderecoPadrao());

        dto.setTipo(logradouroConverter.convertToDatabaseColumn(endereco.getTipo()));

        return dto;
    }

    public String formatarCep(String numero) {
        String numeroStr = String.format("%08d", numero);
        return numeroStr.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
    }
}
