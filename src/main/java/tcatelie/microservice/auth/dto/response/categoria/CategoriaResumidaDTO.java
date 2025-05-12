package tcatelie.microservice.auth.dto.response.categoria;

import lombok.Builder;

@Builder
public record CategoriaResumidaDTO(
        Integer idCategoria,
        String nomeCategoria,
        Boolean categoriaAtiva
) {
}
