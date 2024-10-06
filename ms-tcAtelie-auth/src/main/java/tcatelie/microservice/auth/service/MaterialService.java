package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.repository.MaterialRepository;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository repository;

    public Material findById(Integer idMaterial) throws IllegalArgumentException {
        if (idMaterial == null) {
            throw new IllegalArgumentException("Id do material não informado");
        }

        return repository.findById(idMaterial).orElseThrow(() ->
                new IllegalArgumentException("Material não encontrado com o ID: " + idMaterial)
        );
    }
}
