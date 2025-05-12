package tcatelie.microservice.auth.dto.response.material_produto;

import lombok.Builder;

@Builder
public record MaterialProdutoResumidoResponseDTO(
        Integer idMaterial,
        String nomeMaterial,
        Integer qtdMaterial
) {
}
