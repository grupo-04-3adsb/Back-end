package tcatelie.microservice.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.request.UsuarioClientRequestDTO;
import tcatelie.microservice.auth.dto.response.UsuarioClientResponseDTO;
import tcatelie.microservice.auth.mapper.UsuarioClientMapper;
import tcatelie.microservice.auth.model.UsuarioClient;
import tcatelie.microservice.auth.repository.UsuarioClientRepository;
import tcatelie.microservice.auth.security.AESUtil;

import javax.crypto.SecretKey;
import java.util.Optional;

@Service
public class UsuarioClientService {

    private final UsuarioClientMapper mapper;

    private final UsuarioClientRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioClientService(UsuarioClientMapper mapper, UsuarioClientRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public ResponseEntity<?> login(String email, String senha) {
        try {
            String encryptedEmail = AESUtil.encrypt(email);
            Optional<UsuarioClient> optionalUsuario = Optional.ofNullable(repository.findByEmail(encryptedEmail));

            if (optionalUsuario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
            }
            UsuarioClient entity = optionalUsuario.get();
            UsuarioClientResponseDTO usuarioResponse = mapper.toDTO(entity);

            if (!passwordEncoder.matches(senha, usuarioResponse.getSenha())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }

            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante o processo de login: " + e.getMessage());
        }
    }

    public ResponseEntity<?> cadastrarUsuario(UsuarioClientRequestDTO usuarioRequest) {
        try {
            String encryptedEmail = AESUtil.encrypt(usuarioRequest.getEmail());
            Optional<UsuarioClient> existingUser = Optional.ofNullable(repository.findByEmail(encryptedEmail));

            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com este e-mail.");
            }

            UsuarioClient usuario = mapper.toEntity(usuarioRequest);
            String hashedSenha = passwordEncoder.encode(usuarioRequest.getSenha());
            usuario.setSenha(hashedSenha);

            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante o cadastro do usuário.");
        }
    }

    public ResponseEntity<String> atualizarUsuario(UsuarioClientRequestDTO usuarioRequest) {
        try {
            Optional<UsuarioClient> userId = repository.findById(usuarioRequest.getIdUsuario());

            if (userId.isPresent()) {
                UsuarioClient existingUser = userId.get();

                existingUser.setNomeCompleto(usuarioRequest.getNomeCompleto());
                existingUser.setEmail(usuarioRequest.getEmail());
                existingUser.setSenha(usuarioRequest.getSenha());
                existingUser.setNumeroTelefone(usuarioRequest.getNumeroTelefone());
                existingUser.setCargos(usuarioRequest.getCargos());
                repository.save(existingUser);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Atualização de dados feito com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante a atualização do usuário.");
        }
    }

    public ResponseEntity<String> deletarUsuario(UsuarioClientRequestDTO usuarioRequest) {
        try {
            Optional<UsuarioClient> userId = repository.findById(usuarioRequest.getIdUsuario());

            if (userId.isPresent()) {
                repository.deleteById(usuarioRequest.getIdUsuario());
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body("Delete de usuário feito com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuário não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante o delete do usuário.");
        }
    }
}