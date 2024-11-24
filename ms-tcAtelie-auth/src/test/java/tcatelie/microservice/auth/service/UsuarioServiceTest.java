package tcatelie.microservice.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tcatelie.microservice.auth.dto.AuthenticationDTO;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.UserRole;
import tcatelie.microservice.auth.mapper.UsuarioMapper;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService usuarioService;

	@Mock
	private UserRepository repository;

	@Mock
	private UsuarioMapper usuarioMapper;

	@Mock
	private Authentication authentication;

	@Mock
	private UserDetails userDetails;

	private BCryptPasswordEncoder passwordEncoder;

	private RegisterDTO registerDTO;

	private Usuario usuario;

	@BeforeEach
	void setUp() {
		passwordEncoder = new BCryptPasswordEncoder();

		lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
		lenient().when(userDetails.getUsername()).thenReturn("claudio@gmail.com");

		registerDTO = new RegisterDTO("Cláudio Araújo", "123.456.789-09", "(11) 98765-4325", "claudio@gmail.com",
				"#Gf123456", UserRole.ADMIN, Genero.MASCULINO, "http://img.png", Status.HABILITADO,
				LocalDate.of(2005, 1, 7));

		usuario = new Usuario(1, "Cláudio Araújo", "cludio@gmail", passwordEncoder.encode("#Gf123456"), UserRole.ADMIN,
				"(11) 94463-6705", Status.HABILITADO, LocalDateTime.now(), LocalDateTime.now(), "123.456.789-09",
				Genero.MASCULINO, "img.png", LocalDate.now());
	}

	@Test
	void loadUserByUsername_deveRetornarUsuario_quandoUsuarioExistir() {

		when(repository.findByEmail("cludio@gmail")).thenReturn(Optional.of(usuario));

		UserDetails result = usuarioService.loadUserByUsername("cludio@gmail");

		assertNotNull(result);
		assertEquals("cludio@gmail", result.getUsername());
	}

	@Test
	void loadUserByUsername_deveLancarUsernameNotFoundException_quandoUsuarioNaoExistir() {

		String email = "claudio@gmail";
		when(repository.findByEmail(email)).thenReturn(Optional.empty());

		UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
			usuarioService.loadUserByUsername(email);
		});

		assertEquals("Usuário não encontrado com email: " + email, exception.getMessage());
	}

	@Test
	void verificarPermissoes_deveRetornar404_quandoUsuarioNaoForEncontrado() {
		when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());

		ResponseEntity response = usuarioService.verificarPermissoes(1, authentication);

		assertEquals(404, response.getStatusCodeValue());
		assertEquals("Usuário não encontrado.", response.getBody());
	}

	@Test
	void cadastrarUsuario_deveLancarIllegalArgumentException_quandoUsuarioNaoForMaiorDeIdade() {

		registerDTO.setDataNascimento(LocalDate.now().minusYears(1));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			usuarioService.cadastrarUsuario(registerDTO);
		});

		assertEquals("O usuário deve ser maior de idade", exception.getMessage());
	}

	@Test
	void cadastrarUsuario_deveLancarIllegalArgumentException_quandoEmailJaCadastrado() {

		when(repository.existsByEmail(registerDTO.getEmail())).thenReturn(true);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			usuarioService.cadastrarUsuario(registerDTO);
		});

		assertEquals("Email já cadastrado", exception.getMessage());
	}

	@Test
	void cadastrarUsuario_deveSalvarUsuario_quandoTodosOsRequisitosForemAtendidos() {

		when(repository.existsByEmail(registerDTO.getEmail())).thenReturn(false);
		when(usuarioMapper.toUsuario(registerDTO)).thenReturn(usuario);

		usuarioService.cadastrarUsuario(registerDTO);

		verify(repository, times(1)).save(usuario);
	}

	@Test
	void verificarPermissoes_deveRetornar403_quandoUsuarioSemPermissao() {
		when(repository.findById(1)).thenReturn(Optional.of(usuario));

		ResponseEntity response = usuarioService.verificarPermissoes(1, authentication);

		assertEquals(403, response.getStatusCodeValue());
		assertEquals("Você não tem permissão para realizar esta ação.", response.getBody());
	}

	@Test
	void verificarPermissoes_deveRetornar200_quandoUsuarioValido() {

		usuario.setEmail("claudio@gmail.com");

		when(repository.findById(1)).thenReturn(Optional.of(usuario));

		ResponseEntity response = usuarioService.verificarPermissoes(1, authentication);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(usuario, response.getBody());
	}

	@Test
	void atualizarUsuario_deveAtualizarSomenteCamposBasicos_quandoSenhaVazia() {
		usuario.setEmail("claudio@gmail.com");

		when(repository.findById(1)).thenReturn(Optional.of(usuario));
		when(usuarioMapper.toUsuarioResponseDTO(any(Usuario.class))).thenReturn(mock(UsuarioResponseDTO.class));

		registerDTO.setSenha("");

		ResponseEntity<?> response = usuarioService.atualizarUsuario(1, registerDTO, authentication);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(usuarioMapper.toUsuarioResponseDTO(usuario), response.getBody());

		assertEquals(registerDTO.getEmail(), usuario.getEmail());
		assertEquals(registerDTO.getTelefone(), usuario.getTelefone());
		assertEquals(registerDTO.getImgUrl(), usuario.getUrlImgUsuario());
	}

	@Test
	void atualizarUsuario_deveAtualizarSenhaQuandoInformada() {
		usuario.setEmail("claudio@gmail.com");

		when(repository.findById(1)).thenReturn(Optional.of(usuario));
		when(usuarioMapper.toUsuarioResponseDTO(any(Usuario.class))).thenReturn(mock(UsuarioResponseDTO.class));

		registerDTO.setSenha("#Gf12345678910");

		ResponseEntity<?> response = usuarioService.atualizarUsuario(1, registerDTO, authentication);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(usuarioMapper.toUsuarioResponseDTO(usuario), response.getBody());

		boolean senhaCorreta = passwordEncoder.matches("#Gf12345678910", usuario.getSenha());
		assertEquals(true, senhaCorreta);
	}

	@Test
	void deletarUsuario_deveDeletarUsuario_quandoUsuarioValido() {
		usuario.setEmail("claudio@gmail.com");

		when(repository.findById(1)).thenReturn(Optional.of(usuario));

		ResponseEntity<?> response = usuarioService.deletarUsuario(1, authentication);

		verify(repository, times(1)).deleteById(1);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(null, response.getBody());
	}

	@Test
	void buscarUsuarioEmailSenha_deveRetornar400_quandoDtoForNulo() {
		ResponseEntity<?> response = usuarioService.buscarUsuarioEmailSenha(null);

		assertEquals(400, response.getStatusCodeValue());
		assertEquals("Corpo da requisição inválido.", response.getBody());
	}

	@Test
	void buscarUsuarioEmailSenha_deveRetornar401_quandoEmailNaoForEncontrado() {
		AuthenticationDTO dto = new AuthenticationDTO("claudio@gmail.com", "#Gf123456");

		lenient().when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

		ResponseEntity<?> response = usuarioService.buscarUsuarioEmailSenha(dto);

		assertEquals(401, response.getStatusCodeValue());
		assertEquals("Email ou senha incorretos.", response.getBody());
	}

	@Test
	void buscarUsuarioEmailSenha_deveRetornar401_quandoSenhaIncorreta() {
		AuthenticationDTO dto = new AuthenticationDTO("claudio@gmail.com", "#Gf1234567");

		when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(usuario));

		ResponseEntity<?> response = usuarioService.buscarUsuarioEmailSenha(dto);

		assertEquals(401, response.getStatusCodeValue());
		assertEquals("Email ou senha incorretos.", response.getBody());
	}

	@Test
	void buscarUsuarioEmailSenha_deveRetornar200_quandoEmailESenhaCorretos() {
		AuthenticationDTO dto = new AuthenticationDTO("claudio@gmail.com", "#Gf123456");

		when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(usuario));
		when(usuarioMapper.toUsuarioResponseDTO(any(Usuario.class))).thenReturn(new UsuarioResponseDTO());

		ResponseEntity<?> response = usuarioService.buscarUsuarioEmailSenha(dto);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(new UsuarioResponseDTO(), response.getBody());
	}
}