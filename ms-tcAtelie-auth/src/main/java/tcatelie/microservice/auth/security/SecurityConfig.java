package tcatelie.microservice.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desabilitar CSRF temporariamente para testes
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/clientes/*", "/h2-console/*", "/produtos/*").permitAll() // Permitir acesso público à rota de cadastro
                        .anyRequest().authenticated() // Requer autenticação para todas as outras rotas
                );

        return http.build();
    }
}
