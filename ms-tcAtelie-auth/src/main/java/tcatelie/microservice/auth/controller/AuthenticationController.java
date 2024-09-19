package tcatelie.microservice.auth.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcatelie.microservice.auth.dto.AuthenticationDTO;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.dto.response.LoginResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.repository.UserRepository;
import tcatelie.microservice.auth.service.JwtService;
import tcatelie.microservice.auth.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository repository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UsuarioService service;

	@PostMapping("/admin/login")
	public ResponseEntity loginDashboard(@RequestBody @Valid AuthenticationDTO data) {
		var emailSenha = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
		var auth = this.authenticationManager.authenticate(emailSenha);

		var usuario = (Usuario) auth.getPrincipal();

		boolean isUser = auth.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

		if (!isUser) {
			return ResponseEntity.status(403).body("Acesso negado.");
		}

		var token = jwtService.generateToken(usuario);

		ResponseEntity response = service.buscarUsuarioEmailSenha(data);

		return ResponseEntity.ok(new LoginResponseDTO((UsuarioResponseDTO) response.getBody(), token));
	}

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

		var token = jwtService.generateToken(usuario);

		ResponseEntity response = service.buscarUsuarioEmailSenha(data);

		return ResponseEntity.ok(new LoginResponseDTO((UsuarioResponseDTO) response.getBody(), token));
	}



	@PostMapping("/register")
	public ResponseEntity<?> cadastrarProduto(@RequestBody @Valid RegisterDTO data) {
		try {
			service.cadastrarUsuario(data);
			return ResponseEntity.status(201).body("Cadastro realizado com sucesso.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(400).body("Ocorreu um erro durante o cadastro do produto.");
		}
	}

}
