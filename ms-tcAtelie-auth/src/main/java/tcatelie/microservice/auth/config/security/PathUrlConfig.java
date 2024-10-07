package tcatelie.microservice.auth.config.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class PathUrlConfig {

    public static final AntPathRequestMatcher[] PUBLIC_URLS = {
            new AntPathRequestMatcher("/auth/customer/login"),
            new AntPathRequestMatcher("/auth/register"),
            new AntPathRequestMatcher("/auth/google"),
            new AntPathRequestMatcher("/auth/admin/login"),
            new AntPathRequestMatcher("/auth/validar"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/enderecos/usuario/{usuarioId}", "GET"),
            new AntPathRequestMatcher("/produtos/{idProduto}", "GET")};

    public static final AntPathRequestMatcher[] ADMIN_URLS = {
            new AntPathRequestMatcher("/enderecos", "GET"),
            new AntPathRequestMatcher("/enderecos/{id}", "PUT"),
            new AntPathRequestMatcher("/produtos", "POST"),
            new AntPathRequestMatcher("/materiais/{id}", "PUT"),
    };

    public static final AntPathRequestMatcher[] USER_URLS = {

    };

}
