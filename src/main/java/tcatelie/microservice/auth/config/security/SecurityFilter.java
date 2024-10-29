package tcatelie.microservice.auth.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tcatelie.microservice.auth.repository.UserRepository;
import tcatelie.microservice.auth.service.JwtService;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);
		if (token != null) {
			logger.info("Token recuperado: {}", token);
			var login = jwtService.validateToken(token);
			logger.info("Login após validação do token: {}", login);
			Optional<UserDetails> user = userRepository.findByEmail(login);

			if (user.isPresent()) {
				var authentication = new UsernamePasswordAuthenticationToken(user.get(), null,
						user.get().getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				logger.info("Usuário autenticado: {}", login);
			} else {
				logger.warn("Usuário não encontrado: {}", login);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		} else {
			logger.warn("Nenhum token encontrado no cabeçalho Authorization");
		}
		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null)
			return null;
		return authHeader.replace("Bearer ", "");
	}
}
