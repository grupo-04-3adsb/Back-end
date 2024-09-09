package tcatelie.microservice.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import tcatelie.microservice.auth.dto.request.UsuarioClientRequestDTO;
import tcatelie.microservice.auth.dto.response.UsuarioClientResponseDTO;
import tcatelie.microservice.auth.mapper.UsuarioClientMapper;
import tcatelie.microservice.auth.model.Produto;
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

    public ResponseEntity<String> cadastrarUsuario(UsuarioClientRequestDTO usuarioRequest) {
        try {
            String encryptedEmail = AESUtil.encrypt(usuarioRequest.getEmail());
            Optional<UsuarioClient> existingUser = Optional.ofNullable(repository.findByEmail(encryptedEmail));

            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com este e-mail.");
            }

            UsuarioClient usuario = mapper.toEntity(usuarioRequest);
            String hashedSenha = passwordEncoder.encode(usuarioRequest.getSenha());
            usuario.setSenha(hashedSenha);
            usuario.setEmail(encryptedEmail);
            repository.save(usuario);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante o cadastro do usuário.");
        }
    }

    public ResponseEntity<String> atualizarUsuario(int id, UsuarioClientRequestDTO usuarioRequest) {
        try {
            Optional<UsuarioClient> userId = repository.findById(id);

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

    public ResponseEntity<String> deletarUsuario(int id){
        try{
            Optional<UsuarioClient> userId = repository.findById(id);

            if(userId.isPresent()){
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body("Usuário deletado com sucesso.");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuário não encontrado.");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante o delete do usuário.");
        }
    }
}