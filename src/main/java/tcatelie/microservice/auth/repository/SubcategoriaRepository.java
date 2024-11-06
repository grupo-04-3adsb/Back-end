package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.model.Subcategoria;

import java.util.Optional;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Integer>, JpaSpecificationExecutor<Subcategoria> {
    Optional<Subcategoria> findByNomeSubcategoria(String nome);

    Page<Subcategoria> findByNomeSubcategoriaContainingIgnoreCaseAndCategoria(String nome,Categoria categoria, Pageable pageable);

    Integer countByCategoria_IdCategoria(Integer idCategoria);

}
