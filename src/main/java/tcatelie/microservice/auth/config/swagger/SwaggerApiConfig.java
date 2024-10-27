package tcatelie.microservice.auth.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "API de Autenticação e Gerenciamento do TCAtelie",
				description = "A **API do TCAtelie** oferece funcionalidades robustas para autenticação de usuários, compras de produtos e gerenciamento de dados no e-commerce e dashboard.",
				version = "1.0.0",
				license = @License(name = "UNLICENSED"),
				contact = @Contact(name = "TCAteliê", url = "https://github.com/Claudio712005", email = "tcatelie2023@gmail.com")
		),
		security = @SecurityRequirement(name = "Bearer")
)
@SecurityScheme(
		name = "Bearer",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
public class SwaggerApiConfig {
}
