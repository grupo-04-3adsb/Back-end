package tcatelie.microservice.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.mapper.EnderecoMapper;
import tcatelie.microservice.auth.model.Endereco;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.repository.EnderecoRepository;
import tcatelie.microservice.auth.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnderecoServiceTest {

	@InjectMocks
	private EnderecoService enderecoService;

	@Mock
	private EnderecoRepository repository;

	@Mock
	private UsuarioService usuarioService;

	@Mock
	private EnderecoMapper enderecoMapper;

	@Mock
	private UserRepository userRepository;

	@Mock
	private Authentication authentication;

	private Endereco endereco;
	private EnderecoRequestDTO enderecoRequestDTO;
	private EnderecoResponseDTO enderecoResponseDTO;
	private Usuario usuario;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		usuario = new Usuario();
		usuario.setIdUsuario(1);

		endereco = new Endereco();
		endereco.setId(1);
		endereco.setUsuario(usuario);

		enderecoRequestDTO = new EnderecoRequestDTO();
		enderecoRequestDTO.setRua("Rua A");
		enderecoRequestDTO.setNumero("123");
		enderecoRequestDTO.setCep("12345-678");

		enderecoResponseDTO = new EnderecoResponseDTO();
	}

	@Test
	void cadastrarEndereco_deveCadastrarEndereco_quandoValido() {
		when(usuarioService.verificarPermissoes(anyInt(), any())).thenReturn(ResponseEntity.ok().build());
		when(repository.findByUsuarioIdUsuarioAndCep(anyInt(), anyString())).thenReturn(Optional.empty());
		when(userRepository.existsById(anyInt())).thenReturn(true);
		when(userRepository.getById(anyInt())).thenReturn(usuario);
		when(enderecoMapper.toEndereco(any(EnderecoRequestDTO.class))).thenReturn(endereco);

		ResponseEntity response = enderecoService.cadastrarEndereco(1, enderecoRequestDTO, authentication);

		assertEquals(201, response.getStatusCodeValue());
		assertEquals("Endereço cadastrado", response.getBody());
	}

	@Test
	void cadastrarEndereco_deveRetornar400_quandoEnderecoJaExisteComCep() {
		when(usuarioService.verificarPermissoes(anyInt(), any())).thenReturn(ResponseEntity.ok().build());
		when(repository.findByUsuarioIdUsuarioAndCep(anyInt(), anyString())).thenReturn(Optional.of(endereco));

		ResponseEntity response = enderecoService.cadastrarEndereco(1, enderecoRequestDTO, authentication);

		assertEquals(400, response.getStatusCodeValue());
		assertEquals("Esse usuário já tem um endereço com o cep informado.", response.getBody());
	}

	@Test
	void cadastrarEndereco_deveRetornar404_quandoUsuarioNaoEncontrado() {
		when(usuarioService.verificarPermissoes(anyInt(), any())).thenReturn(ResponseEntity.ok().build());
		when(repository.findByUsuarioIdUsuarioAndCep(anyInt(), anyString())).thenReturn(Optional.empty());
		when(userRepository.existsById(anyInt())).thenReturn(false);

		ResponseEntity response = enderecoService.cadastrarEndereco(1, enderecoRequestDTO, authentication);

		assertEquals(404, response.getStatusCodeValue());
		assertEquals("O usuário não foi encontrado.", response.getBody());
	}

	@Test
	void atualizarEndereco_deveRetornar200_quandoCamposValidos() {
		when(repository.existsById(anyInt())).thenReturn(true);
		when(repository.findById(anyInt())).thenReturn(Optional.of(endereco));
		when(usuarioService.verificarPermissoes(anyInt(), any())).thenReturn(ResponseEntity.ok().build());
		when(enderecoMapper.toEndereco(any(EnderecoRequestDTO.class))).thenReturn(endereco);

		ResponseEntity response = enderecoService.atualizarEndereco(1, enderecoRequestDTO, authentication);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Endereço atualizado com sucesso.", response.getBody());
	}

	@Test
	void atualizarEndereco_deveRetornar404_quandoEnderecoNaoEncontrado() {
		when(repository.existsById(anyInt())).thenReturn(false);

		ResponseEntity response = enderecoService.atualizarEndereco(1, enderecoRequestDTO, authentication);

		assertEquals(404, response.getStatusCodeValue());
		assertEquals("Endereço não encontrado.", response.getBody());
	}

	@Test
	void deletarEndereco_deveExcluirERetornar200_quandoEnderecoValido() {
		when(repository.existsById(anyInt())).thenReturn(true);
		when(repository.findById(anyInt())).thenReturn(Optional.of(endereco));
		when(usuarioService.verificarPermissoes(anyInt(), any())).thenReturn(ResponseEntity.ok().build());

		ResponseEntity response = enderecoService.deletarEndereco(1, authentication);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Endereço deletado com sucesso.", response.getBody());
	}

	@Test
	void deletarEndereco_deveRetornar404_quandoEnderecoNaoEncontrado() {
		when(repository.existsById(anyInt())).thenReturn(false);

		ResponseEntity response = enderecoService.deletarEndereco(1, authentication);

		assertEquals(404, response.getStatusCodeValue());
		assertEquals("Endereço não encontrado.", response.getBody());
	}

	@Test
	void obterTodosEnderecos_deveRetornarListaEndereco_quandoValido() {
		when(repository.findAll()).thenReturn(List.of(endereco));
		when(enderecoMapper.toEnderecoResponseDTO(any(Endereco.class))).thenReturn(enderecoResponseDTO);

		ResponseEntity response = enderecoService.obterTodosEnderecos();

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(List.of(enderecoResponseDTO), response.getBody());
	}

	@Test
	void obterTodosEnderecos_deveRetornar204_quandoListaEnderecosVazia() {
		when(repository.findAll()).thenReturn(List.of());

		ResponseEntity response = enderecoService.obterTodosEnderecos();

		assertEquals(204, response.getStatusCodeValue());
	}

	@Test
	void obterEnderecoPorId_deveRetornarEndereco_quandoEnderecoValido() {
		when(repository.findById(anyInt())).thenReturn(Optional.of(endereco));
		when(enderecoMapper.toEnderecoResponseDTO(any(Endereco.class))).thenReturn(enderecoResponseDTO);

		ResponseEntity response = enderecoService.obterEnderecoPorId(1);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(enderecoResponseDTO, response.getBody());
	}

	@Test
	void obterEnderecoPorId_deveRetornar404_quandoEnderecoNaoEncontrado() {
		when(repository.findById(anyInt())).thenReturn(Optional.empty());

		ResponseEntity response = enderecoService.obterEnderecoPorId(1);

		assertEquals(404, response.getStatusCodeValue());
		assertEquals("Endereço não encontrado.", response.getBody());
	}

	@Test
	void obterEnderecosPorUsuario_deveRetornarListaEnderecos_quandoIdUsuarioValido() {
		when(userRepository.existsById(anyInt())).thenReturn(true);
		when(usuarioService.verificarPermissoes(anyInt(), any())).thenReturn(ResponseEntity.ok().build());
		when(repository.findByUsuarioIdUsuario(anyInt())).thenReturn(List.of(endereco));
		when(enderecoMapper.toEnderecoResponseDTO(any(Endereco.class))).thenReturn(enderecoResponseDTO);

		ResponseEntity response = enderecoService.obterEnderecosPorUsuario(1, authentication);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(List.of(enderecoResponseDTO), response.getBody());
	}

	@Test
	void obterEnderecosPorUsuario_deveRetornar404_quandoIdUsuarioNaoEncontrado() {
		when(userRepository.existsById(anyInt())).thenReturn(false);

		ResponseEntity response = enderecoService.obterEnderecosPorUsuario(1, authentication);

		assertEquals(404, response.getStatusCodeValue());
		assertEquals("O usuário não foi encontrado.", response.getBody());
	}

	@Test
	void obterEnderecosPorUsuario_deveRetornar403_quandoUsuarioSemPermissao() {
		when(userRepository.existsById(anyInt())).thenReturn(true);
		when(usuarioService.verificarPermissoes(anyInt(), any())).thenReturn(ResponseEntity.status(403).build());

		ResponseEntity response = enderecoService.obterEnderecosPorUsuario(1, authentication);

		assertEquals(403, response.getStatusCodeValue());
	}
}
