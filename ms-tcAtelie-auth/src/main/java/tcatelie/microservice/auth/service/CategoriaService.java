package tcatelie.microservice.auth.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.filter.ProdutoFiltroDTO;
import tcatelie.microservice.auth.dto.request.CategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.mapper.CategoriaMapper;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.repository.CategoriaRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.specification.ProdutoSpecification;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;
    private final ProdutoRepository produtoRepository;

    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO requestDTO) {
        validarRequest(requestDTO);
        Categoria categoriaEntidade = mapper.toCategoria(requestDTO);

        Optional<Categoria> categoriaBuscada = repository.findByNomeCategoria(requestDTO.getNome());
        if(categoriaBuscada.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Categoria categoriaSalva = repository.save(categoriaEntidade);
        return mapper.toCategoriaResponse(categoriaSalva);
    }

    private void validarRequest(CategoriaRequestDTO requestDTO) {
        if (requestDTO == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O corpo da requisição não foi informado");
    }

    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO requestDTO) {
        validarRequest(requestDTO);
        Categoria categoriaEntidade = mapper.toCategoria(requestDTO);

        Optional<Categoria> categoriaBuscada = repository.findByNomeCategoria(requestDTO.getNome());
        if(categoriaBuscada.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
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

    public Categoria findById(Integer id) throws IllegalArgumentException{
        return repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Categoria não encontrada com o id: " + id)
        );
    }

    public Page<CategoriaResponseDTO> pesquisarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeCategoriaContainingIgnoreCase(nome, pageable).map(c -> mapper.toCategoriaResponse(c));
    }

    public List<CategoriaResponseDTO> listarCategoria() {
        List<Categoria> categorias = repository.findAll();
        return categorias.stream()
                .map(mapper::toCategoriaResponse).toList();
    }

    public CategoriaResponseDTO atualizar(@Valid CategoriaRequestDTO categoriaRequestDTO, Integer id) {
        if(repository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Categoria categoriaEntidade = mapper.toCategoria(categoriaRequestDTO);
        categoriaEntidade.setIdCategoria(id);
        categoriaEntidade.setNomeCategoria(categoriaRequestDTO.getNome());
        Categoria categoriaSalva = repository.save(categoriaEntidade);

        Categoria categoriaEntidade = findById(id);
        categoriaEntidade.setNomeCategoria(categoriaRequestDTO.getNome());
        Categoria categoriaSalva = repository.save(categoriaEntidade);
        return mapper.toCategoriaResponse(categoriaSalva);
    }

    public void deletar(Integer id) {
        if(repository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        List<Produto> listaProdutos = produtoRepository.findAll(ProdutoSpecification.filtrar(ProdutoFiltroDTO.builder().idCategoria(id).build()));

        if(listaProdutos.isEmpty()){
            repository.deleteById(id);
        }


    }
}
