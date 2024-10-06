package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("enderecos")
@Tag(name = "Endereços", description = "Gerenciamento de endereços de usuários")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@Operation(summary = "Cadastra um novo endereço para um usuário", description = "Este endpoint permite que um usuário cadastrado adicione um novo endereço ao seu perfil, utilizando seu ID. O endereço deve ser único por CEP.", responses = {
			@ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Erro: o CEP já está cadastrado para este usuário.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Erro: usuário não encontrado.", content = @Content(mediaType = "application/json")) })
	@PostMapping("{idUsuario}")
	public ResponseEntity cadastrarEndereco(
			@Parameter(description = "ID do usuário que está cadastrando o endereço") Integer idUsuario,
			@RequestBody @Valid EnderecoRequestDTO dto, Authentication authentication) {
		return enderecoService.cadastrarEndereco(idUsuario, dto, authentication);
	}

	@Operation(summary = "Atualiza um endereço existente", description = "Este endpoint permite que um usuário edite um endereço cadastrado pelo seu ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Erro: endereço não encontrado.", content = @Content(mediaType = "application/json")) })
	@PutMapping("/{id}")
	public ResponseEntity atualizarEndereco(@Parameter(description = "ID do endereço a ser atualizado") Integer id,
			@RequestBody EnderecoRequestDTO dto, Authentication authentication) {
		return enderecoService.atualizarEndereco(id, dto, authentication);
	}

	@Operation(summary = "Deleta um endereço existente", description = "Este endpoint permite que um usuário delete um endereço cadastrado pelo seu ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Endereço deletado com sucesso", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Erro: endereço não encontrado.", content = @Content(mediaType = "application/json")) })
	@DeleteMapping("/{id}")
	public ResponseEntity deletarEndereco(@Parameter(description = "ID do endereço a ser deletado") Integer id,
			Authentication authentication) {
		return enderecoService.deletarEndereco(id, authentication);
	}

	@Operation(summary = "Obtém todos os endereços cadastrados", description = "Este endpoint retorna todos os endereços cadastrados no sistema.", responses = {
			@ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class))),
			@ApiResponse(responseCode = "204", description = "Nenhum endereço encontrado.", content = @Content(mediaType = "application/json")) })
	@GetMapping
	public ResponseEntity obterTodosEnderecos() {
		return enderecoService.obterTodosEnderecos();
	}

	@Operation(summary = "Obtém um endereço específico pelo ID", description = "Este endpoint permite que um usuário obtenha os detalhes de um endereço específico pelo seu ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Erro: endereço não encontrado.", content = @Content(mediaType = "application/json")) })
	@GetMapping("/{id}")
	public ResponseEntity obterEnderecoPorId(@Parameter(description = "ID do endereço a ser obtido") Integer id) {
		return enderecoService.obterEnderecoPorId(id);
	}

	@Operation(summary = "Obtém todos os endereços de um usuário específico", description = "Este endpoint permite que um usuário obtenha todos os endereços associados ao seu perfil.", responses = {
			@ApiResponse(responseCode = "200", description = "Endereços encontrados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Erro: usuário não encontrado.", content = @Content(mediaType = "application/json")) })
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity obterEnderecosPorUsuario(
			@Parameter(description = "ID do usuário cujos endereços serão obtidos") Integer usuarioId,
			Authentication authentication) {
		return enderecoService.obterEnderecosPorUsuario(usuarioId, authentication);
	}
}
