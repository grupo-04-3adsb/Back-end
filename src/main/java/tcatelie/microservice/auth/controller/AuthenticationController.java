package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.AuthenticationDTO;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.request.GoogleAuthDTO;
import tcatelie.microservice.auth.dto.response.LoginResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.mapper.UsuarioMapper;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.service.JwtService;
import tcatelie.microservice.auth.service.UsuarioService;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação de usuários.")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UsuarioService service;
  private final UsuarioMapper mapper;

  @Operation(summary = "Realiza o login para os usuários com nível 'ADMIN' na dashboard.", description = "Endpoint para autenticação de usuários administradores. Retorna um token JWT para acesso aos recursos restritos da dashboard.", responses = {
          @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso e token gerado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class))),
          @ApiResponse(responseCode = "403", description = "Usuário encontrado porém não autorizado. Somente usuários com a role 'ADMIN' podem acessar."),
          @ApiResponse(responseCode = "401", description = "Email ou senha incorretos.")})
  @PostMapping("/admin/login")
  public ResponseEntity<?> loginDashboard(@RequestBody @Valid AuthenticationDTO data) {
    var emailSenha = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
    var auth = this.authenticationManager.authenticate(emailSenha);
    var usuario = (Usuario) auth.getPrincipal();

    boolean isUser = auth.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

    if (!isUser) {
      return ResponseEntity.status(403).body("Acesso negado.");
    }

    var token = jwtService.generateAccessToken(usuario);
    var refreshToken = jwtService.generateRefreshToken(usuario);
    ResponseEntity response = service.buscarUsuarioEmailSenha(data);

    return ResponseEntity.ok(new LoginResponseDTO((UsuarioResponseDTO) response.getBody(), token, refreshToken));
  }

  @Operation(summary = "Realiza o login para os usuários na plataforma de e-commerce.", description = "Endpoint para autenticação de clientes da plataforma de e-commerce. Retorna um token JWT para acesso a recursos da plataforma.", responses = {
          @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso e token gerado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class))),
          @ApiResponse(responseCode = "403", description = "Acesso negado. Apenas clientes podem acessar."),
          @ApiResponse(responseCode = "401", description = "Email ou senha incorretos.")})
  @PostMapping("/customer/login")
  public ResponseEntity<?> loginEcommerce(@RequestBody @Valid AuthenticationDTO data) {
    var emailSenha = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
    var auth = this.authenticationManager.authenticate(emailSenha);
    var usuario = (Usuario) auth.getPrincipal();

    boolean isUser = auth.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));

    boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

    if (isAdmin || !isUser) {
      return ResponseEntity.status(403).body("Acesso negado. Apenas clientes podem acessar.");
    }

    var token = jwtService.generateAccessToken(usuario);
    var refreshToken = jwtService.generateRefreshToken(usuario);
    ResponseEntity response = service.buscarUsuarioEmailSenha(data);

    return ResponseEntity.ok(new LoginResponseDTO((UsuarioResponseDTO) response.getBody(), token, refreshToken));
  }

  @Operation(summary = "Cadastra um novo usuário na plataforma.", description = "Endpoint para registro de um novo usuário. Verifica se o usuário é maior de idade e se os campos são válidos antes de cadastrar.", responses = {
          @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterDTO.class))),
          @ApiResponse(responseCode = "400", description = "O usuário deve ser maior de idade ou campos inválidos."),
          @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrados.")})
  @PostMapping("/register")
  public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid RegisterDTO data) {
    return service.cadastrarUsuario(data);
  }

  @Operation(summary = "Realiza a autenticação do usuário via Google.", description = "Endpoint que permite que um usuário se autentique utilizando suas credenciais do Google.", responses = {
          @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso.", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos."),
          @ApiResponse(responseCode = "409", description = "Email já cadastrado.")})
  @PostMapping("/google")
  public ResponseEntity<?> autenticacaoGoogle(@RequestBody @Valid GoogleAuthDTO googleAuthDTO) {
    return service.autenticacaoGoogle(googleAuthDTO, authenticationManager);
  }

  @Operation(summary = "Valida se um email e CPF podem ser utilizados para cadastro.", description = "Endpoint que verifica se um email e CPF já estão cadastrados, liberando o usuário para o registro.", responses = {
          @ApiResponse(responseCode = "200", description = "Usuário liberado para cadastro.", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Ambos os campos devem ser preenchidos."),
          @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrados.")})
  @PostMapping("/validar")
  public ResponseEntity<?> liberarCadastro(@RequestParam @Parameter(description = "Email do usuário") String email,
                                           @RequestParam @Parameter(description = "CPF do usuário") String cpf) {
    return service.buscarPorEmailECPF(email, cpf);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshAccessToken(@RequestBody String refreshToken) {
    if (jwtService.validateRefreshToken(refreshToken)) {
      String email = jwtService.validateToken(refreshToken);
      Usuario usuario = (Usuario) service.loadUserByUsername(email);

      String newAccessToken = jwtService.generateAccessToken(usuario);
      String newRefreshToken = jwtService.generateRefreshToken(usuario);

      return ResponseEntity.ok(new LoginResponseDTO(mapper.toUsuarioResponseDTO(usuario), newAccessToken, newRefreshToken));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido ou expirado.");
    }
  }

  @PostMapping("/recover-forgot-password")
  public ResponseEntity forgotPassword(@RequestParam String email) {
    if (email == null || email.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email não pode ser nulo ou vazio.");
    }

    String token = jwtService.generateForgotPasswordToken(email);
    service.enviarEmailRecuperacaoSenha(email, token);

    return ResponseEntity.ok("Email de recuperação de senha enviado com sucesso.");
  }

  @PostMapping("/send-new-password")
  public ResponseEntity<String> sendNewPassword(
          @RequestHeader("Authorization") String authHeader,
          @RequestParam @Valid String newPassword
  ) {
    if (newPassword == null || newPassword.isBlank() || newPassword.length() < 6) {
      return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body("Nova senha não pode ser nula, vazia ou ter menos de 6 caracteres.");
    }

    String token = authHeader.replace("Bearer ", "");

    jwtService.validateForgotPasswordToken(token);

    String email = jwtService.getEmailFromForgotPasswordToken(token);

    service.atualizaSenha(email, newPassword);

    return ResponseEntity.ok("Senha atualizada com sucesso.");
  }
}
