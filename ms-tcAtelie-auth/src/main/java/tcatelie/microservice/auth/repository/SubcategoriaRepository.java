package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Subcategoria;

import java.util.Optional;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Integer> {
    Optional<Subcategoria> findByNomeSubcategoria(String nome);

}
