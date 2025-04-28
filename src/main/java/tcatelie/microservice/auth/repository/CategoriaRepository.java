package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import tcatelie.microservice.auth.dto.kpi.CategoriaKPIDTO;
import tcatelie.microservice.auth.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>, JpaSpecificationExecutor<Categoria> {
  Optional<Categoria> findByNomeCategoria(String nome);

  Page<Categoria> findByNomeCategoriaContainingIgnoreCase(String nome, Pageable pageable);

  @Query("""
              SELECT new tcatelie.microservice.auth.dto.kpi.CategoriaKPIDTO(
                  c.nomeCategoria,
                  COUNT(DISTINCT p.id),
                  0.0,
                  c.codigoCor
              )
              FROM Pedido p
              JOIN p.itens i
              JOIN i.produto pr
              JOIN pr.categoria c
              WHERE p.status = 'CONCLUIDO'
              GROUP BY c.idCategoria
              ORDER BY COUNT(DISTINCT p.id) DESC
          """)
  Page<CategoriaKPIDTO> buscarCategoriaMaisVendida(Pageable page);


}
