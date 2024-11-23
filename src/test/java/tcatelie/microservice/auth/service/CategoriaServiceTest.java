package tcatelie.microservice.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.filter.CategoriaFiltroDTO;
import tcatelie.microservice.auth.dto.request.CategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.mapper.CategoriaMapper;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.repository.CategoriaRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.repository.SubcategoriaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository repository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private SubcategoriaRepository subcategoriaRepository;

    @Mock
    private CategoriaMapper mapper;

    @Mock
    private Pageable pageable;

    @Mock
    private CategoriaFiltroDTO filtroDTO;

    @Test
    void testCadastrarCategoria() {
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();
        requestDTO.setNome("Categoria Teste");
        requestDTO.setCodigoCor("FF0000");

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setIdCategoria(1);
        categoriaSalva.setNomeCategoria("Categoria Teste");
        categoriaSalva.setCodigoCor("FF0000");

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO();
        responseDTO.setIdCategoria(1);
        responseDTO.setNomeCategoria("Categoria Teste");

        when(repository.findByNomeCategoria("Categoria Teste")).thenReturn(Optional.empty());
        when(mapper.toCategoria(requestDTO)).thenReturn(categoriaSalva);
        when(repository.save(categoriaSalva)).thenReturn(categoriaSalva);
        when(mapper.toCategoriaResponse(categoriaSalva)).thenReturn(responseDTO);

        CategoriaResponseDTO result = categoriaService.cadastrarCategoria(requestDTO);

        assertNotNull(result);
        assertEquals("Categoria Teste", result.getNomeCategoria());
        verify(repository).save(categoriaSalva);
    }

    @Test
    void testCadastrarCategoriaComNomeDuplicado() {
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();
        requestDTO.setNome("Categoria Teste");
        requestDTO.setCodigoCor("FF0000");

        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setIdCategoria(1);
        categoriaExistente.setNomeCategoria("Categoria Teste");

        when(repository.findByNomeCategoria("Categoria Teste")).thenReturn(Optional.of(categoriaExistente));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            categoriaService.cadastrarCategoria(requestDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Já existe uma categoria com o nome Categoria Teste", exception.getReason());
    }

    @Test
    void testFindByNome() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNomeCategoria("Categoria Teste");

        when(repository.findByNomeCategoria("Categoria Teste")).thenReturn(Optional.of(categoria));

        Categoria result = categoriaService.findByNome("Categoria Teste");

        assertNotNull(result);
        assertEquals("Categoria Teste", result.getNomeCategoria());
    }

    @Test
    void testFindByNomeComNomeVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.findByNome("");
        });

        assertEquals("Nome da categoria não informado", exception.getMessage());
    }

    @Test
    void testFindById() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNomeCategoria("Categoria Teste");

        when(repository.findById(1)).thenReturn(Optional.of(categoria));

        Categoria result = categoriaService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getIdCategoria());
    }

    @Test
    void testFindByIdComIdNaoEncontrado() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoriaService.findById(999);
        });

        assertEquals("Categoria não encontrada com o id: 999", exception.getMessage());
    }

    @Test
    void testPesquisar() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNomeCategoria("Categoria Teste");

        Page<Categoria> categoriasPage = new PageImpl<>(Collections.singletonList(categoria));

        doReturn(new PageImpl<>(Collections.singletonList(categoria)))
                .when(repository).findAll(any(Specification.class), eq(pageable));
        when(mapper.toCategoriaResponse(categoria)).thenReturn(new CategoriaResponseDTO());

        Page<CategoriaResponseDTO> result = categoriaService.pesquisar(pageable, filtroDTO);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testListarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNomeCategoria("Categoria Teste");

        when(repository.findAll()).thenReturn(Collections.singletonList(categoria));
        when(mapper.toCategoriaResponse(categoria)).thenReturn(new CategoriaResponseDTO());

        List<CategoriaResponseDTO> result = categoriaService.listarCategoria();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testAtualizar() {
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();
        requestDTO.setNome("Categoria Atualizada");
        requestDTO.setCodigoCor("00FF00");

        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setIdCategoria(1);
        categoriaExistente.setNomeCategoria("Categoria Teste");

        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setIdCategoria(1);
        categoriaAtualizada.setNomeCategoria("Categoria Atualizada");
        categoriaAtualizada.setCodigoCor("00FF00");

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO();
        responseDTO.setIdCategoria(1);
        responseDTO.setNomeCategoria("Categoria Atualizada");

        when(repository.findById(1)).thenReturn(Optional.of(categoriaExistente));
        when(repository.findByNomeCategoria("Categoria Atualizada")).thenReturn(Optional.empty());
        when(repository.save(categoriaExistente)).thenReturn(categoriaAtualizada);
        when(mapper.toCategoriaResponse(categoriaAtualizada)).thenReturn(responseDTO);

        CategoriaResponseDTO result = categoriaService.atualizar(requestDTO, 1);

        assertNotNull(result);
        assertEquals("Categoria Atualizada", result.getNomeCategoria());
    }

    @Test
    void testDeletar() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNomeCategoria("Categoria Teste");

        when(repository.findById(1)).thenReturn(Optional.of(categoria));
        when(produtoRepository.countByCategoria_IdCategoria(1)).thenReturn(0);

        ResponseEntity<?> result = categoriaService.deletar(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(repository).save(categoria);
    }

    @Test
    void testDeletarComProdutosAssociados() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNomeCategoria("Categoria Teste");

        when(repository.findById(1)).thenReturn(Optional.of(categoria));
        when(produtoRepository.countByCategoria_IdCategoria(1)).thenReturn(5);

        ResponseEntity<?> result = categoriaService.deletar(1);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }
}
