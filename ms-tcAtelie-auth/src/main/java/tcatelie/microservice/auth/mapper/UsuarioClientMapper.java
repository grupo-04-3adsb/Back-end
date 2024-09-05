package tcatelie.microservice.auth.mapper;

import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.UsuarioClientRequestDTO;
import tcatelie.microservice.auth.dto.response.UsuarioClientResponseDTO;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.model.UsuarioClient;
import tcatelie.microservice.auth.security.AESUtil;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class UsuarioClientMapper {
    private final EnderecoMapper enderecoMapper = new EnderecoMapper();

    public UsuarioClient toEntity(UsuarioClientRequestDTO requestDTO) throws Exception {
        UsuarioClient usuarioClient = new UsuarioClient();
        usuarioClient.setNome(requestDTO.getNome());
        usuarioClient.setNomeCompleto(AESUtil.encrypt(requestDTO.getNomeCompleto()));
        usuarioClient.setEmail(AESUtil.encrypt(requestDTO.getEmail()));
        usuarioClient.setSenha(AESUtil.encrypt(requestDTO.getSenha()));
        usuarioClient.setDataNascimento(requestDTO.getDataNascimento());
        usuarioClient.setGenero(Genero.fromDisplayName(requestDTO.getGenero()));
        usuarioClient.setListaEnderecos(requestDTO.getListaEnderecos().stream().map(e -> {
            try {
                return enderecoMapper.toEntity(e);
            } catch (Exception ex) {
                throw new RuntimeException("Não foi possível Mapear o endereço para a entidade.");
            }
        }).collect(Collectors.toList()));
        usuarioClient.setNumeroTelefone(AESUtil.encrypt(requestDTO.getNumeroTelefone().replaceAll("\\D+", "")));
        usuarioClient.setDataCriacao(LocalDateTime.now());
        usuarioClient.setDataAtualizacao(LocalDateTime.now());
        usuarioClient.setCpf(AESUtil.encrypt(requestDTO.getCpf().replaceAll("\\D+", "")));
        return usuarioClient;
    }

    public UsuarioClientResponseDTO toDTO(UsuarioClient entity) throws Exception {
        UsuarioClientResponseDTO dto = new UsuarioClientResponseDTO();
        dto.setIdUser(entity.getIdUsuario());
        dto.setCpf(formatarCpf(AESUtil.decrypt(entity.getCpf())));
        dto.setGenero(entity.getGenero().getDescricao());
        dto.setNome(entity.getNome());
        dto.setSenha(entity.getSenha());
        dto.setNomeCompleto(AESUtil.decrypt(entity.getNomeCompleto()));
        dto.setEmail(AESUtil.decrypt(entity.getEmail()));
        dto.setNumeroTelefone(formatarTelefone(AESUtil.decrypt(entity.getNumeroTelefone())));
        dto.setDataAtualizacao(entity.getDataAtualizacao());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setListaEnderecos(entity.getListaEnderecos().stream().map(e -> {
            try {
                return enderecoMapper.toDto(e);
            } catch (Exception ex) {
                throw new RuntimeException("Não foi possível Mapear o endereço para o DTO.");
            }
        }).collect(Collectors.toList()));
        return dto;
    }

    public String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("Número de CPF inválido. Deve ter 11 dígitos.");
        }
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public String formatarTelefone(String numero) {
        return numero.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "$1-$2-$3");
    }
}
