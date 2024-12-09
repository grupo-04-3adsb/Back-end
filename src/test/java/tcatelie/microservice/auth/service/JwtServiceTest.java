package tcatelie.microservice.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.UserRole;
import tcatelie.microservice.auth.model.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;
    private String secret = "secret";
    private long accessExpirationHours = 1;
    private long refreshExpirationDays = 7;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();

        // Configuração manual dos campos anotados com @Value usando reflexão
        var secretField = JwtService.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtService, secret);

        var accessExpirationField = JwtService.class.getDeclaredField("accessExpirationHours");
        accessExpirationField.setAccessible(true);
        accessExpirationField.set(jwtService, accessExpirationHours);

        var refreshExpirationField = JwtService.class.getDeclaredField("refreshExpirationDays");
        refreshExpirationField.setAccessible(true);
        refreshExpirationField.set(jwtService, refreshExpirationDays);
    }

    @Test
    void generateAccessToken_deveGerarTokenValido_quandoUsuarioForValido() {
        Usuario usuario = createUsuario();

        String token = jwtService.generateAccessToken(usuario);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void generateRefreshToken_deveGerarTokenValido_quandoUsuarioForValido() {
        Usuario usuario = createUsuario();

        String token = jwtService.generateRefreshToken(usuario);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void validateToken_deveRetornarEmail_quandoTokenForValido() {
        Usuario usuario = createUsuario();
        String token = jwtService.generateAccessToken(usuario);

        String result = jwtService.validateToken(token);

        assertEquals(usuario.getEmail(), result);
    }

    @Test
    void validateToken_deveRetornarStringVazia_quandoTokenForInvalido() {
        String invalidToken = "invalidToken";

        String result = jwtService.validateToken(invalidToken);

        assertEquals("", result);
    }

    @Test
    void validateToken_deveRetornarStringVazia_quandoTokenEstiverExpirado() throws InterruptedException {
        Usuario usuario = createUsuario();

        // Gera um token com tempo de expiração curto
        String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject(usuario.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))
                .sign(Algorithm.HMAC256(secret));

        String result = jwtService.validateToken(token);

        assertEquals("", result);
    }

    @Test
    void validateRefreshToken_deveRetornarTrue_quandoTokenForValido() {
        Usuario usuario = createUsuario();
        String refreshToken = jwtService.generateRefreshToken(usuario);

        boolean isValid = jwtService.validateRefreshToken(refreshToken);

        assertTrue(isValid);
    }

    @Test
    void validateRefreshToken_deveRetornarFalse_quandoTokenForInvalido() {
        String invalidToken = "invalidToken";

        boolean isValid = jwtService.validateRefreshToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void validateRefreshToken_deveRetornarFalse_quandoTokenEstiverExpirado() {
        Usuario usuario = createUsuario();

        String expiredToken = JWT.create()
                .withIssuer("auth-api")
                .withSubject(usuario.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() - 1000))
                .withClaim("tokenType", "refresh")
                .sign(Algorithm.HMAC256(secret));

        boolean isValid = jwtService.validateRefreshToken(expiredToken);

        assertFalse(isValid);
    }

    private Usuario createUsuario() {
        return new Usuario(
                1,
                "Cláudio Araújo",
                "claudio@gmail.com",
                "#Gf1234567",
                UserRole.ADMIN,
                "(11) 94463-6705",
                Status.HABILITADO,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "123.456.789-09",
                Genero.MASCULINO,
                "img.png",
                LocalDate.now()
        );
    }
}
