package tcatelie.microservice.auth.config.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class PathUrlConfig {

    public static final AntPathRequestMatcher[] PUBLIC_URLS = {
            new AntPathRequestMatcher("/auth/**"),
            new AntPathRequestMatcher("/auth/register"),
            new AntPathRequestMatcher("/auth/google"),
            new AntPathRequestMatcher("/auth/admin/login", "POST"),
            new AntPathRequestMatcher("/auth/validar"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/enderecos/usuario/{usuarioId}", "GET"),
            new AntPathRequestMatcher("/produtos", "GET"),
            new AntPathRequestMatcher("/produtos/{idProduto}", "GET"),
            new AntPathRequestMatcher("/produtos/nome/{nomeProduto}", "GET"),
            new AntPathRequestMatcher("/categorias", "GET"),
            new AntPathRequestMatcher("/categorias/{id}", "GET"),
            new AntPathRequestMatcher("/subcategorias", "GET"),
            new AntPathRequestMatcher("/subcategorias/{id}", "GET"),
            new AntPathRequestMatcher("/categorias/pesquisar"),
            new AntPathRequestMatcher("/subcategorias/pesquisar"),
            new AntPathRequestMatcher("/subcategorias/filtrar"),
            new AntPathRequestMatcher("/materiais/pesquisar/**"),
            new AntPathRequestMatcher("/api/v1/mercadopago/**"),
            new AntPathRequestMatcher("/produtos/mercado-livre"),
            new AntPathRequestMatcher("/produtos/produtos/exportar-csv"),
            new AntPathRequestMatcher("/auth/refresh-token"),
            new AntPathRequestMatcher("/materiais/filtrar"),
            new AntPathRequestMatcher("/avaliacoes", "POST"),
            new AntPathRequestMatcher("/avaliacoes/produto/{produtoId}", "GET"),
            new AntPathRequestMatcher("/avaliacoes/produto/{produtoId}/media"),
            new AntPathRequestMatcher("/api/v1/mercadopago/payment"),
            new AntPathRequestMatcher("/calcular-fretes/**"),
            new AntPathRequestMatcher("/pedidos/carrinho/{idUsuario}", "GET"),
            new AntPathRequestMatcher("/item-pedidos/{idItemPedido}"),
            new AntPathRequestMatcher("/item-pedidos"),
            new AntPathRequestMatcher("/produtos/pesquisar/nome-sku"),
            new AntPathRequestMatcher("/calcular-fretes/pedido/{id}"),
            new AntPathRequestMatcher("/calcular-fretes/{cep}", "POST"),
            new AntPathRequestMatcher("/item-pedidos/{idItemPedido}/quantidade"),
            new AntPathRequestMatcher("/produtos/sugerir-produtos"),
            new AntPathRequestMatcher("/pedidos/{idProduto}", "GET"),
            new AntPathRequestMatcher("/pedidos/carrinho/{idUsuario}"),
            new AntPathRequestMatcher("/pedidos/{idPedido}", "PUT"),
            new AntPathRequestMatcher("/pedidos/{id}/ultimo")
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
            new AntPathRequestMatcher("/materiais/revisao", "POST"),
            new AntPathRequestMatcher("/produtos/listar-por-material/**"),
            new AntPathRequestMatcher("/usuarios/admins", "GET"),
            new AntPathRequestMatcher("/pedidos/listar"),
            new AntPathRequestMatcher("/pedidos", "POST"),
            new AntPathRequestMatcher("/custos-outros"),
            new AntPathRequestMatcher("/usuarios/pesquisar-admins"),
            new AntPathRequestMatcher("/usuarios/responsavel"),
    };

    public static final AntPathRequestMatcher[] USER_URLS = {
            new AntPathRequestMatcher("/item-pedidos/**")
    };
}
