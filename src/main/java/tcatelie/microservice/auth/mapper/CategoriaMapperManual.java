package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.response.categoria.CategoriaResumidaDTO;
import tcatelie.microservice.auth.model.Categoria;

public class CategoriaMapperManual {

    public static CategoriaResumidaDTO toCategoriaResumidaDTO(Categoria categoria) {
        return CategoriaResumidaDTO.builder()
                .idCategoria(categoria.getIdCategoria())
                .nomeCategoria(categoria.getNomeCategoria())
                .categoriaAtiva(categoria.getCategoriaAtiva())
                .build();
    }

}
