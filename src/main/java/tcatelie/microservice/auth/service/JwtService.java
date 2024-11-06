package tcatelie.microservice.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.model.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

	@Value("${api.security.token.secret}")
	private String secret;

	@Value("${api.security.token.accessExpirationHours}")
	private long accessExpirationHours;

	@Value("${api.security.token.refreshExpirationDays}")
	private long refreshExpirationDays;

	public String generateAccessToken(Usuario usuario) {
		return generateToken(usuario, accessExpirationHours, "access");
	}

	public String generateRefreshToken(Usuario usuario) {
		return generateToken(usuario, refreshExpirationDays * 24, "refresh");
	}

	private String generateToken(Usuario usuario, long expirationHours, String tokenType) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("auth-api")
					.withSubject(usuario.getEmail())
					.withExpiresAt(genExpirationDate(expirationHours))
					.withClaim("tokenType", tokenType)
					.sign(algorithm);
		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro na geração do token", e);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			return "";
		}
	}

	public boolean validateRefreshToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			var decodedJWT = JWT.require(algorithm)
					.withIssuer("auth-api")
					.withClaim("tokenType", "refresh")
					.build()
					.verify(token);

			return decodedJWT.getExpiresAt().toInstant().isAfter(Instant.now());
		} catch (JWTVerificationException e) {
			return false;
		}
	}

	private Instant genExpirationDate(long hours) {
		return LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.of("-03:00"));
	}
}
