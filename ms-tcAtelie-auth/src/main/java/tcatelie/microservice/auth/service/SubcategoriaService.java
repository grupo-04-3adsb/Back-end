package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.model.Subcategoria;
import tcatelie.microservice.auth.repository.SubcategoriaRepository;

@Service
@RequiredArgsConstructor
public class SubcategoriaService {

    private final SubcategoriaRepository repository;

    public Subcategoria findByNome(String nome) throws IllegalArgumentException {
        if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("Nome da subcategoria não informado");
        }

        return repository.findByNomeSubcategoria(nome).orElseThrow(() ->
                new IllegalArgumentException("Subcategoria não encontrada com o nome: " + nome)
        );
    }
}
