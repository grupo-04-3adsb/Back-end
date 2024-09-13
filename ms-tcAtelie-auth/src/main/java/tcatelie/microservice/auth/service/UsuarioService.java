package tcatelie.microservice.auth.service;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tcatelie.microservice.auth.dto.AuthenticationDTO;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.mapper.UsuarioMapper;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.repository.UserRepository;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

	private final UserRepository repository;

	private final UsuarioMapper usuarioMapper;

	public UsuarioService(UserRepository repository, UsuarioMapper usuarioMapper) {
		this.repository = repository;
		this.usuarioMapper = usuarioMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));
	}

	public void cadastrarUsuario(RegisterDTO dto) {
		if (!dto.isMaiorDeIdade()) {
			throw new IllegalArgumentException("O usuário deve ser maior de idade");
		}

		dto.setStatus(Status.HABILITADO);

		if (repository.existsByEmail(dto.getEmail())) {
			throw new IllegalArgumentException("Email já cadastrado");
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encryptedPassword = passwordEncoder.encode(dto.getSenha());

		Usuario usuario = usuarioMapper.toUsuario(dto);
		usuario.setSenha(encryptedPassword);

		repository.save(usuario);
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
}
