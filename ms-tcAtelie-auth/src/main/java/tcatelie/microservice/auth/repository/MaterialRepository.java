package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Material;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    Optional<Material> findByNomeMaterial(String nome);

    Page<Material> findByNomeMaterialContainingIgnoreCase(String nome, Pageable pageable);

}
