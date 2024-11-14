package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tcatelie.microservice.auth.model.Categoria;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>, JpaSpecificationExecutor<Categoria> {
    Optional<Categoria> findByNomeCategoria(String nome);

    Page<Categoria> findByNomeCategoriaContainingIgnoreCase(String nome, Pageable pageable);

}
