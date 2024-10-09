package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Categoria;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByNomeCategoria(String nome);
}
