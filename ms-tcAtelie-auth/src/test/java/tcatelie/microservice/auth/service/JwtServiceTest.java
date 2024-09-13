package tcatelie.microservice.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.UserRole;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.service.JwtService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

	private JwtService jwtService;

	@Value("${api.security.token.secret}")
	private String secret = "secret";

	@BeforeEach
	void setUp() {
		jwtService = new JwtService();
		jwtService.secret = secret;
	}

	@Test
	void generateToken_deveGerarTokenValido_quandoUsuarioForValido() {
		Usuario usuario = new Usuario(1, "Cláudio Araújo", "claudio@gmail.com", "#Gf1234567", UserRole.ADMIN,
				"(11) 94463-6705", Status.HABILITADO, LocalDateTime.now(), LocalDateTime.now(), "123.456.789-09",
				Genero.MASCULINO, "img.png", LocalDate.now());

		String token = jwtService.generateToken(usuario);

		assertNotNull(token);
		assertTrue(token.startsWith("eyJ"));
	}

	@Test
	void validateToken_deveRetornarEmail_quandoTokenForValido() {
		String email = "claudio@gmail.com";
		String token = JWT.create().withIssuer("auth-api").withSubject(email)
				.withExpiresAt(jwtService.genExpirationDate()).sign(Algorithm.HMAC256(secret));

		String result = jwtService.validateToken(token);

		assertEquals(email, result);
	}

	@Test
	void validateToken_deveRetornarStringVazia_quandoTokenForInvalido() {
		String invalidToken = "invalidToken";

		String result = jwtService.validateToken(invalidToken);

		assertEquals("", result);
	}

}
