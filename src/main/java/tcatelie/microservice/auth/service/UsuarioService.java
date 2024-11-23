package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.AuthenticationDTO;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.request.GoogleAuthDTO;
import tcatelie.microservice.auth.dto.response.LoginResponseDTO;
import tcatelie.microservice.auth.dto.response.ResponsavelResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.UserRole;
import tcatelie.microservice.auth.mapper.UsuarioMapper;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.model.ResponsavelPedido;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.repository.PedidoRepository;
import tcatelie.microservice.auth.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UserRepository repository;

    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PedidoRepository pedidoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));
    }

    public ResponseEntity<String> buscarPorEmailECPF(String email, String cpf) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(cpf)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambos os campos devem ser preenchidos.");
        }

        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        if (repository.existsByCpf(cpf)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Usuário liberado para cadastro");
    }

    public ResponseEntity<?> cadastrarUsuario(RegisterDTO dto) {
        if (!dto.isMaiorDeIdade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário deve ser maior de idade");
        }

        dto.setStatus(Status.HABILITADO);

        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");

        }

        if (repository.existsByCpf(dto.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF em uso");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(dto.getSenha());

        Usuario usuario = usuarioMapper.toUsuario(dto);
        usuario.setSenha(encryptedPassword);

        usuario = repository.save(usuario);

        String token = jwtService.generateAccessToken(usuario);

        UsuarioResponseDTO usuarioResponseDTO = usuarioMapper.toUsuarioResponseDTO(usuario);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(usuarioResponseDTO, token);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loginResponseDTO);
    }

    public ResponseEntity buscarUsuarioEmailSenha(AuthenticationDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(400).body("Corpo da requisição inválido.");
        }

        Optional<UserDetails> optUsuario = repository.findByEmail(dto.getEmail());

        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(401).body("Email ou senha incorretos.");
        }

        Usuario usuario = (Usuario) optUsuario.get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(401).body("Email ou senha incorretos.");
        }

        return ResponseEntity.status(200).body(usuarioMapper.toUsuarioResponseDTO(usuario));
    }

    public ResponseEntity<?> atualizarUsuario(Integer id, RegisterDTO dto, Authentication authentication) {
        ResponseEntity<?> response = verificarPermissoes(id, authentication);
        if (response.getStatusCode().value() != 200) {
            return response;
        }

        Usuario usuarioAtual = (Usuario) response.getBody();

        usuarioAtual.setTelefone(dto.getTelefone());
        usuarioAtual.setUrlImgUsuario(dto.getImgUrl());
        usuarioAtual.setEmail(dto.getEmail());

        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            usuarioAtual.setStatus(dto.getStatus());
            usuarioAtual.setRole(dto.getRole());
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!StringUtils.isEmpty(dto.getSenha())) {
            String encryptedPassword = passwordEncoder.encode(dto.getSenha());
            usuarioAtual.setSenha(encryptedPassword);
        }

        repository.save(usuarioAtual);
        return ResponseEntity.status(200).body(usuarioMapper.toUsuarioResponseDTO(usuarioAtual));
    }

    public ResponseEntity<?> deletarUsuario(Integer id, Authentication authentication) {
        ResponseEntity<?> response = verificarPermissoes(id, authentication);
        if (response.getStatusCode().value() != 200) {
            return response;
        }

        Usuario usuarioAtual = (Usuario) response.getBody();
        repository.deleteById(usuarioAtual.getIdUsuario());
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<?> verificarPermissoes(Integer id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailUsuarioAutenticado = userDetails.getUsername();

        Optional<Usuario> usuarioOptional = repository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        Usuario usuarioAtual = usuarioOptional.get();
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !usuarioAtual.getEmail().equals(emailUsuarioAutenticado)) {
            return ResponseEntity.status(403).body("Você não tem permissão para realizar esta ação.");
        }

        return ResponseEntity.status(200).body(usuarioAtual);
    }

    public ResponseEntity<?> autenticacaoGoogle(GoogleAuthDTO googleAuthDTO, AuthenticationManager authenticationManager) {
        Optional<UserDetails> usuarioExistente = repository.findByEmail(googleAuthDTO.getEmail());
        Usuario usuario;

        if (usuarioExistente.isEmpty()) {
            usuario = new Usuario();
            usuario.setNome(googleAuthDTO.getName());
            usuario.setEmail(googleAuthDTO.getEmail());
            usuario.setIdGoogle(googleAuthDTO.getSub());
            usuario.setUrlImgUsuario(googleAuthDTO.getPicture());
            usuario.setStatus(Status.HABILITADO);
            usuario.setRole(UserRole.USER);
            usuario.setDthrCadastro(LocalDateTime.now());
            usuario.setDthrAtualizacao(LocalDateTime.now());

            String senhaRandomica = passwordEncoder.encode("senhaAleatoriaGoogle");
            usuario.setSenha(senhaRandomica);

            usuario = repository.save(usuario);
        } else {
            usuario = (Usuario) usuarioExistente.get();
            if (usuario.getIdGoogle() == null) {
                usuario.setIdGoogle(googleAuthDTO.getSub());
                usuario.setUrlImgUsuario(googleAuthDTO.getPicture());
                repository.save(usuario);
            }
        }

        String token = jwtService.generateAccessToken(usuario);

        UsuarioResponseDTO usuarioResponseDTO = usuarioMapper.toUsuarioResponseDTO(usuario);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(usuarioResponseDTO, token);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }

    public List<ResponsavelResponseDTO> buscarAdmins() {
        return repository
                .findByRole(UserRole.ADMIN)
                .stream().map(usuarioMapper::toResponsavelResponseDTO)
                .toList();
    }

    public List<ResponsavelResponseDTO> pesquisarAdmins(String nome) {
        return repository
                .findByRoleAndNomeContainingIgnoreCase(UserRole.ADMIN, nome)
                .stream().map(usuarioMapper::toResponsavelResponseDTO)
                .toList();
    }

    public ResponseEntity adicionarResponsavelPedido(Integer idPedido, Integer idUsuario) {
        Usuario usuario = repository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Responsável não encontrado"));

        if (usuario.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas administradores podem ser responsáveis");
        }

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if (pedido.getResponsaveis()
                .stream().anyMatch(responsavel -> responsavel
                        .getResponsavel().getIdUsuario().equals(idUsuario))) {

            throw new ResponseStatusException(HttpStatus.CONFLICT, "Responsável já é responsável deste pedido");
        }

        pedido.getResponsaveis().add(new ResponsavelPedido(
                usuario, pedido, LocalDateTime.now(), LocalDateTime.now()
        ));

        pedidoRepository.save(pedido);

        return ResponseEntity.ok().body("Responsável adicionado com sucesso");
    }

    public ResponseEntity removerResponsavelPedido(Integer idPedido, Integer idUsuario) {
        Usuario usuario = repository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Responsável não encontrado"));

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        ResponsavelPedido responsavelPedido = pedido.getResponsaveis()
                .stream().filter(responsavel -> responsavel
                        .getResponsavel().getIdUsuario().equals(idUsuario)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Responsável não encontrado"));

        pedido.getResponsaveis().remove(responsavelPedido);
        pedidoRepository.save(pedido);

        return ResponseEntity.noContent().build();
    }
}
