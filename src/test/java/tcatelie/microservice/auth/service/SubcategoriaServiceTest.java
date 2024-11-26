package tcatelie.microservice.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.filter.SubcategoriaFiltroDTO;
import tcatelie.microservice.auth.dto.request.SubcategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.mapper.SubcategoriaMapper;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.model.Subcategoria;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.repository.SubcategoriaRepository;
import tcatelie.microservice.auth.specification.SubcategoriaSpecification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubcategoriaServiceTest {
    @Mock
    private SubcategoriaRepository repository;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private SubcategoriaMapper mapper;

    @InjectMocks
    private SubcategoriaService service;

    @Mock
    private SubcategoriaSpecification specification;

    @Mock
    private SubcategoriaFiltroDTO filtroDTO;

    @Mock
    private Pageable pageable;

    @Test
    void testCadastrarSubcategoria_Sucesso() {
        SubcategoriaRequestDTO requestDTO = new SubcategoriaRequestDTO();
        requestDTO.setNomeSubcategoria("Subcategoria1");
        requestDTO.setIdCategoria(1);

        Categoria categoriaMock = new Categoria();
        categoriaMock.setIdCategoria(1);

        Subcategoria subcategoriaMock = new Subcategoria();
        subcategoriaMock.setIdSubcategoria(1);
        subcategoriaMock.setNomeSubcategoria("Subcategoria1");

        when(categoriaService.findById(1)).thenReturn(categoriaMock);
        when(repository.findByNomeSubcategoria("Subcategoria1")).thenReturn(Optional.empty());
        when(mapper.toSubcategoria(requestDTO)).thenReturn(subcategoriaMock);
        when(repository.save(subcategoriaMock)).thenReturn(subcategoriaMock);
        when(mapper.toSubcategoriaResponse(subcategoriaMock)).thenReturn(new SubcategoriaResponseDTO());

        SubcategoriaResponseDTO response = service.cadastrarSubcategoria(requestDTO);

        assertNotNull(response);
        verify(repository, times(1)).save(subcategoriaMock);
    }

    @Test
    void testCadastrarSubcategoria_CategoriaNaoInformada() {
        SubcategoriaRequestDTO requestDTO = new SubcategoriaRequestDTO();
        requestDTO.setNomeSubcategoria("Subcategoria1");

        assertThrows(ResponseStatusException.class, () -> {
            service.cadastrarSubcategoria(requestDTO);
        });
    }

    @Test
    void testCadastrarSubcategoria_SubcategoriaExistente() {
        SubcategoriaRequestDTO requestDTO = new SubcategoriaRequestDTO();
        requestDTO.setNomeSubcategoria("Subcategoria1");
        requestDTO.setIdCategoria(1);

        Categoria categoriaMock = new Categoria();
        categoriaMock.setIdCategoria(1);

        when(categoriaService.findById(1)).thenReturn(categoriaMock);
        when(mapper.toSubcategoria(any())).thenReturn(new Subcategoria());
        when(repository.findByNomeSubcategoria("Subcategoria1")).thenReturn(Optional.of(new Subcategoria()));

        assertThrows(ResponseStatusException.class, () -> {
            service.cadastrarSubcategoria(requestDTO);
        });
    }

    @Test
    void testFindByNome_Sucesso() {
        Subcategoria subcategoriaMock = new Subcategoria();
        subcategoriaMock.setNomeSubcategoria("Subcategoria1");

        when(repository.findByNomeSubcategoria("Subcategoria1")).thenReturn(Optional.of(subcategoriaMock));

        Subcategoria result = service.findByNome("Subcategoria1");

        assertNotNull(result);
        assertEquals("Subcategoria1", result.getNomeSubcategoria());
    }

    @Test
    void testFindByNome_NomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.findByNome("");
        });
    }

    @Test
    void testFindByNome_SubcategoriaNaoEncontrada() {
        when(repository.findByNomeSubcategoria("SubcategoriaInexistente")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            service.findByNome("SubcategoriaInexistente");
        });
    }

    @Test
    void testPesquisarPorNome_Sucesso() {
        Subcategoria subcategoriaMock = new Subcategoria();
        subcategoriaMock.setIdSubcategoria(1);
        subcategoriaMock.setNomeSubcategoria("Subcategoria1");

        Page<Subcategoria> subcategoriasPage = new PageImpl<>(List.of(subcategoriaMock));
        when(repository.findByNomeSubcategoriaContainingIgnoreCaseAndCategoria(anyString(), any(), any(Pageable.class)))
                .thenReturn(subcategoriasPage);
        when(mapper.toSubcategoriaResponse(subcategoriaMock)).thenReturn(new SubcategoriaResponseDTO());

        Page<SubcategoriaResponseDTO> result = service.pesquisarPorNome("Subcategoria1", 1, PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindById_Sucesso() {
        Subcategoria subcategoriaMock = new Subcategoria();
        subcategoriaMock.setIdSubcategoria(1);

        when(repository.findById(1)).thenReturn(Optional.of(subcategoriaMock));

        Subcategoria result = service.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getIdSubcategoria());
    }

    @Test
    void testFindById_SubcategoriaNaoEncontrada() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            service.findById(1);
        });
    }

    @Test
    void testListarSubcategorias_Sucesso() {
        Subcategoria subcategoriaMock = new Subcategoria();
        subcategoriaMock.setIdSubcategoria(1);

        when(repository.findAll()).thenReturn(List.of(subcategoriaMock));
        when(mapper.toSubcategoriaResponse(subcategoriaMock)).thenReturn(new SubcategoriaResponseDTO());

        List<SubcategoriaResponseDTO> result = service.ListarSubscategorias();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testAtualizar_Sucesso() {
        SubcategoriaRequestDTO requestDTO = new SubcategoriaRequestDTO();
        requestDTO.setNomeSubcategoria("SubcategoriaAtualizada");

        Subcategoria subcategoriaMock = new Subcategoria();
        subcategoriaMock.setIdSubcategoria(1);
        subcategoriaMock.setNomeSubcategoria("Subcategoria1");

        when(repository.findById(1)).thenReturn(Optional.of(subcategoriaMock));
        when(repository.save(subcategoriaMock)).thenReturn(subcategoriaMock);
        when(mapper.toSubcategoriaResponse(subcategoriaMock)).thenReturn(new SubcategoriaResponseDTO());

        SubcategoriaResponseDTO response = service.atualizar(requestDTO, 1);

        assertNotNull(response);
        assertEquals("SubcategoriaAtualizada", subcategoriaMock.getNomeSubcategoria());
    }

    @Test
    void testAtualizar_SubcategoriaNaoEncontrada() {
        SubcategoriaRequestDTO requestDTO = new SubcategoriaRequestDTO();
        requestDTO.setNomeSubcategoria("SubcategoriaAtualizada");

        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.atualizar(requestDTO, 1);
        });
    }

    @Test
    void testDeletar_Sucesso() {
        Subcategoria subcategoriaMock = new Subcategoria();
        subcategoriaMock.setIdSubcategoria(1);

        when(repository.findById(1)).thenReturn(Optional.of(subcategoriaMock));
        when(produtoRepository.countBySubcategoria_IdSubcategoria(1)).thenReturn(0);

        service.deletar(1);

        verify(repository, times(1)).save(subcategoriaMock);
    }

    @Test
    void testDeletar_SubcategoriaComProdutos() {
        Subcategoria subcategoriaMock = new Subcategoria();
        subcategoriaMock.setIdSubcategoria(1);

        when(repository.findById(1)).thenReturn(Optional.of(subcategoriaMock));
        when(produtoRepository.countBySubcategoria_IdSubcategoria(1)).thenReturn(5);

        assertThrows(ResponseStatusException.class, () -> {
            service.deletar(1);
        });
    }

    @Test
    void testFiltrar() {
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setIdSubcategoria(1);

        SubcategoriaResponseDTO responseDTO = new SubcategoriaResponseDTO();
        responseDTO.setIdSubcategoria(1);

        doReturn(new PageImpl<>(Collections.singletonList(subcategoria)))
                .when(repository).findAll(any(Specification.class), eq(pageable));
        when(mapper.toSubcategoriaResponse(subcategoria)).thenReturn(responseDTO);
        when(produtoRepository.countBySubcategoria_IdSubcategoria(1)).thenReturn(10);

        Page<SubcategoriaResponseDTO> result = service.filtrar(filtroDTO, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(10, result.getContent().get(0).getQtdProdutosSubcategoria());
    }

}
