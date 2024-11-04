package tcatelie.microservice.auth.config.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class PathUrlConfig {

    public static final AntPathRequestMatcher[] PUBLIC_URLS = {
            new AntPathRequestMatcher("/auth/customer/login"),
            new AntPathRequestMatcher("/auth/register"),
            new AntPathRequestMatcher("/auth/google"),
            new AntPathRequestMatcher("/auth/admin/login", "POST"),
            new AntPathRequestMatcher("/auth/validar"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/enderecos/usuario/{usuarioId}", "GET"),
            new AntPathRequestMatcher("/produtos", "GET"),
            new AntPathRequestMatcher("/produtos/{idProduto}", "GET"),
            new AntPathRequestMatcher("/categorias", "GET"),
            new AntPathRequestMatcher("/categorias/{id}", "GET"),
            new AntPathRequestMatcher("/subcategorias", "GET"),
            new AntPathRequestMatcher("/subcategorias/{id}", "GET"),
            new AntPathRequestMatcher("/categorias/pesquisar"),
            new AntPathRequestMatcher("/subcategorias/pesquisar"),
            new AntPathRequestMatcher("/subcategorias/filtrar"),
            new AntPathRequestMatcher("/materiais/pesquisar/**"),
            new AntPathRequestMatcher("/produtos/mercado-livre"),
            new AntPathRequestMatcher("/produtos/produtos/exportar-csv"),
            new AntPathRequestMatcher("/auth/refresh-token"),
            new AntPathRequestMatcher("/materiais/filtrar"),
    };

    public static final AntPathRequestMatcher[] ADMIN_URLS = {
            new AntPathRequestMatcher("/enderecos", "GET"),
            new AntPathRequestMatcher("/enderecos/{id}", "PUT"),
            new AntPathRequestMatcher("/produtos", "POST"),
            new AntPathRequestMatcher("/materiais/{id}", "PUT"),
            new AntPathRequestMatcher("/categorias", "POST"),
            new AntPathRequestMatcher("/subcategorias", "POST"),
            new AntPathRequestMatcher("/subcategorias/{id}", "PUT"),
            new AntPathRequestMatcher("/produtos/desativar/"),
            new AntPathRequestMatcher("/produtos/{id}", "PUT"),
            new AntPathRequestMatcher("/produtos", "PUT"),
            new AntPathRequestMatcher("/materiais/revisao", "POST"),
            new AntPathRequestMatcher("/produtos/listar-por-material/**")
    };

    public static final AntPathRequestMatcher[] USER_URLS = {

    };

}
