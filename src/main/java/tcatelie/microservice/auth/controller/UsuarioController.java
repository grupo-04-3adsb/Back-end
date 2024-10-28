package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.AuthenticationDTO;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.service.UsuarioService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de contas de usuários")
@RequestMapping("/usuarios")
public class UsuarioController {

	private final UsuarioService service;

	@Operation(summary = "Autentica o usuário", description = "Este endpoint autentica um usuário usando seu email e senha. Retorna um objeto que representa o usuário se a autenticação for bem-sucedida.", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Corpo da requisição inválido.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Email ou senha incorretos.", content = @Content(mediaType = "application/json")) })
	@PostMapping
	public ResponseEntity buscarUsuarioPorEmailSenha(@RequestBody @Valid AuthenticationDTO dto) {
		return service.buscarUsuarioEmailSenha(dto);
	}

	@Operation(summary = "Atualiza um usuário", description = "Este endpoint permite que um usuário atualizado altere suas informações como telefone, email e senha.", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Corpo da requisição inválido.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para realizar esta ação.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json")) })
	@PutMapping("/{id}")
	public ResponseEntity atualizarUsuario(@Parameter(description = "ID do usuário a ser atualizado") Integer id,
			@RequestBody @Valid RegisterDTO dto, Authentication authentication) {
		return service.atualizarUsuario(id, dto, authentication);
	}

	@Operation(summary = "Deleta um usuário", description = "Este endpoint permite que um usuário delete sua conta. Apenas o próprio usuário ou um administrador pode realizar esta ação.", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para realizar esta ação.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json")) })
	@DeleteMapping("{id}")
	public ResponseEntity deletarUsuario(@Parameter(description = "ID do usuário a ser deletado") Integer id,
			Authentication authentication) {
		return service.deletarUsuario(id, authentication);
	}
}
