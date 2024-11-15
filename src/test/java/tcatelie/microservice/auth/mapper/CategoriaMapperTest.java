package tcatelie.microservice.auth.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.model.Categoria;

import java.time.LocalDateTime;

public class CategoriaMapperTest {

    private final CategoriaMapper categoriaMapper = CategoriaMapper.INSTANCE;

    @Test
    public void testToCategoriaResponse() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNomeCategoria("Cadernos");
        categoria.setDthrCadastro(LocalDateTime.of(2024, 10, 1, 12, 0));
        categoria.setDthrAtualizacao(LocalDateTime.of(2024, 10, 1, 15, 30));

        CategoriaResponseDTO responseDTO = categoriaMapper.toCategoriaResponse(categoria);

        assertEquals("12:00 | 01/10/2024", responseDTO.getDthrCadastro());
        assertEquals("15:30 | 01/10/2024", responseDTO.getDthrAtualizacao());
    }
}
