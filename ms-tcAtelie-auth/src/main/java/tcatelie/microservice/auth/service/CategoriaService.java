package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.mapper.CategoriaMapper;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.repository.CategoriaRepository;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

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
}
