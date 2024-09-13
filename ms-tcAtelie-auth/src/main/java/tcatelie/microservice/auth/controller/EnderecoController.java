package tcatelie.microservice.auth.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.service.EnderecoService;

@RestController
@RequestMapping("enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@PostMapping("{idUsuario}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity cadastrarEndereco(@PathVariable Integer idUsuario, @RequestBody @Valid EnderecoRequestDTO dto,
			Authentication authentication) {
		return enderecoService.cadastrarEndereco(idUsuario, dto, authentication);
	}

	@PutMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity atualizarEndereco(@PathVariable Integer id, @RequestBody EnderecoRequestDTO dto,
			Authentication authentication) {
		return enderecoService.atualizarEndereco(id, dto, authentication);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity deletarEndereco(@PathVariable Integer id, Authentication authentication) {
		return enderecoService.deletarEndereco(id, authentication);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity obterTodosEnderecos() {
		return enderecoService.obterTodosEnderecos();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity obterEnderecoPorId(@PathVariable Integer id) {
		return enderecoService.obterEnderecoPorId(id);
	}

	@GetMapping("/usuario/{usuarioId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity obterEnderecosPorUsuario(@PathVariable Integer usuarioId, Authentication authentication) {
		return enderecoService.obterEnderecosPorUsuario(usuarioId, authentication);
	}
}
