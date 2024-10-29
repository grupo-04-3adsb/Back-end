package tcatelie.microservice.auth.service;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.mapper.EnderecoMapper;
import tcatelie.microservice.auth.model.Endereco;
import tcatelie.microservice.auth.repository.EnderecoRepository;
import tcatelie.microservice.auth.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EnderecoMapper mapper;

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity cadastrarEndereco(Integer idUsuario, EnderecoRequestDTO dto, Authentication authentication) {
		ResponseEntity response = usuarioService.verificarPermissoes(idUsuario, authentication);
		if (response.getStatusCode().value() != 200) {
			return response;
		}

		if (repository.findByUsuarioIdUsuarioAndCep(idUsuario, dto.getCep()).isPresent()) {
			return ResponseEntity.status(400).body("Esse usuário já tem um endereço com o cep informado.");
		}

		Endereco endereco = mapper.toEndereco(dto);

		if (!userRepository.existsById(idUsuario)) {
			return ResponseEntity.status(404).body("O usuário não foi encontrado.");
		}

		endereco.setUsuario(userRepository.getById(idUsuario));

		repository.save(endereco);

		return ResponseEntity.status(201).body("Endereço cadastrado");
	}

	public ResponseEntity atualizarEndereco(Integer id, EnderecoRequestDTO dto, Authentication authentication) {

		if (!repository.existsById(id)) {
			return ResponseEntity.status(404).body("Endereço não encontrado.");
		}

		Endereco endereco = repository.findById(id).get();

		ResponseEntity response = usuarioService.verificarPermissoes(endereco.getUsuario().getIdUsuario(),
				authentication);
		if (response.getStatusCode().value() != 200) {
			return response;
		}

		Endereco enderecoAlterado = mapper.toEndereco(dto);

		endereco.setRua(enderecoAlterado.getRua());
		endereco.setNumero(enderecoAlterado.getNumero());
		endereco.setComplemento(enderecoAlterado.getComplemento());
		endereco.setBairro(enderecoAlterado.getBairro());
		endereco.setCidade(enderecoAlterado.getCidade());
		endereco.setEstado(enderecoAlterado.getEstado());
		endereco.setCep(enderecoAlterado.getCep());
		endereco.setPais(enderecoAlterado.getPais());
		endereco.setInstrucaoEntrega(enderecoAlterado.getInstrucaoEntrega());
		endereco.setLogradouro(enderecoAlterado.getLogradouro());
		endereco.setEnderecoPadrao(endereco.isEnderecoPadrao());

		repository.save(endereco);

		return ResponseEntity.status(200).body("Endereço atualizado com sucesso.");
	}

	public ResponseEntity deletarEndereco(Integer id, Authentication authentication) {

		if (!repository.existsById(id)) {
			return ResponseEntity.status(404).body("Endereço não encontrado.");
		}

		Endereco endereco = repository.findById(id).get();

		ResponseEntity response = usuarioService.verificarPermissoes(endereco.getUsuario().getIdUsuario(),
				authentication);
		if (response.getStatusCode().value() != 200) {
			return response;
		}

		repository.delete(endereco);

		return ResponseEntity.status(200).body("Endereço deletado com sucesso.");
	}

	public ResponseEntity obterTodosEnderecos() {
		List<Endereco> enderecos = repository.findAll();
		List<EnderecoResponseDTO> enderecosDTO = enderecos.stream().map(mapper::toEnderecoResponseDTO)
				.collect(Collectors.toList());

		if (enderecosDTO.isEmpty()) {
			return ResponseEntity.status(204).build();
		}
		return ResponseEntity.status(200).body(enderecosDTO);
	}

	public ResponseEntity obterEnderecoPorId(Integer id) {
		Optional<Endereco> enderecoOptional = repository.findById(id);
		if (enderecoOptional.isPresent()) {
			Endereco endereco = enderecoOptional.get();
			EnderecoResponseDTO enderecoDTO = mapper.toEnderecoResponseDTO(endereco);
			return ResponseEntity.ok(enderecoDTO);
		} else {
			return ResponseEntity.status(404).body("Endereço não encontrado.");
		}
	}

	public ResponseEntity obterEnderecosPorUsuario(Integer usuarioId, Authentication authentication) {

		if (!userRepository.existsById(usuarioId)) {
			return ResponseEntity.status(404).body("O usuário não foi encontrado.");
		}

		ResponseEntity response = usuarioService.verificarPermissoes(usuarioId, authentication);
		if (response.getStatusCode().value() != 200) {
			return response;
		}

		List<Endereco> enderecos = repository.findByUsuarioIdUsuario(usuarioId);
		List<EnderecoResponseDTO> enderecosDTO = enderecos.stream().map(mapper::toEnderecoResponseDTO)
				.collect(Collectors.toList());

		if (enderecosDTO.isEmpty()) {
			return ResponseEntity.status(204).build();
		}
		return ResponseEntity.status(200).body(enderecosDTO);
	}

}
