package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.repository.CategoriaRepository;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public Categoria findByNome(String nome) throws IllegalArgumentException {
        if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("Nome da categoria não informado");
        }

        return repository.findByNomeCategoria(nome).orElseThrow(() ->
                new IllegalArgumentException("Categoria não encontrada com o nome: " + nome)
        );
    }
}
