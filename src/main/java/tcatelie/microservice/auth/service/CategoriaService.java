package tcatelie.microservice.auth.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.filter.CategoriaFiltroDTO;
import tcatelie.microservice.auth.dto.request.CategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.mapper.CategoriaMapper;
import tcatelie.microservice.auth.mapper.ProdutoMapper;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.repository.CategoriaRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.repository.SubcategoriaRepository;
import tcatelie.microservice.auth.specification.CategoriaSpecification;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;
    private final ProdutoMapper produtoMapper;
    private final ProdutoRepository produtoRepository;
    private final SubcategoriaRepository subcategoriaRepository;

    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO requestDTO) {
        validarRequest(requestDTO);
        Categoria categoriaEntidade = mapper.toCategoria(requestDTO);

        Optional<Categoria> categoriaBuscada = repository.findByNomeCategoria(requestDTO.getNome());
        if (categoriaBuscada.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma categoria com o nome " + requestDTO.getNome());
        }

        Categoria categoriaSalva = repository.save(categoriaEntidade);
        return mapper.toCategoriaResponse(categoriaSalva);
    }

    private void validarRequest(CategoriaRequestDTO requestDTO) {
        if (requestDTO == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O corpo da requisição não foi informado");
    }

    public Categoria findByNome(String nome) throws IllegalArgumentException {
        if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("Nome da categoria não informado");
        }

        return repository.findByNomeCategoria(nome).orElseThrow(() ->
                new IllegalArgumentException("Categoria não encontrada com o nome: " + nome)
        );
    }

    public Categoria findById(Integer id) throws IllegalArgumentException {
        return repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Categoria não encontrada com o id: " + id)
        );
    }

    public Page<CategoriaResponseDTO> pesquisar(Pageable pageable, CategoriaFiltroDTO filtro) {
        Page<Categoria> categorias = repository.findAll(CategoriaSpecification.filtrar(filtro), pageable);

        return categorias.map(c -> {
            var categoriaResponse = mapper.toCategoriaResponse(c);
            contarProdutosESubcategorias(categoriaResponse);
            return categoriaResponse;
        });
    }

    public List<CategoriaResponseDTO> listarCategoria() {
        List<Categoria> categorias = repository.findAll();
        return categorias.stream()
                .map(mapper::toCategoriaResponse).toList();
    }

    public CategoriaResponseDTO atualizar(@Valid CategoriaRequestDTO categoriaRequestDTO, Integer id) {
        if (repository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }

        Categoria categoriaEntidade = findById(id);
        categoriaEntidade.setNomeCategoria(categoriaRequestDTO.getNome());
        categoriaEntidade.setCodigoCor(categoriaRequestDTO.getCodigoCor());

        Optional<Categoria> categoriaBusca = repository.findByNomeCategoria(categoriaRequestDTO.getNome());

        if (categoriaBusca.isPresent()) {
            Categoria categoriaEncontrada = categoriaBusca.get();
            if (categoriaEncontrada.getIdCategoria() != id) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma categoria com o nome " + categoriaRequestDTO.getNome());
            }
        }

        Categoria categoriaSalva = repository.save(categoriaEntidade);
        return mapper.toCategoriaResponse(categoriaSalva);
    }

    public ResponseEntity deletar(Integer id) {
        Categoria categoria = findById(id);

        Integer qtdProdutos = produtoRepository.countByCategoria_IdCategoria(id);

        if (qtdProdutos == 0) {
            categoria.setCategoriaAtiva(false);
            repository.save(categoria);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível deletar a categoria pois existem %s produtos associados a ela.".formatted(qtdProdutos));
        }

    }

    public void contarProdutosESubcategorias(CategoriaResponseDTO categoriaResponseDTO) {
        Integer qtdProdutos = produtoRepository.countByCategoria_IdCategoria(categoriaResponseDTO.getIdCategoria());
        Integer qtdSubcategorias = subcategoriaRepository.countByCategoria_IdCategoria(categoriaResponseDTO.getIdCategoria());
        categoriaResponseDTO.setQtdProdutosCategoria(qtdProdutos);
        categoriaResponseDTO.setQtdSubcategorias(qtdSubcategorias);
    }

}
