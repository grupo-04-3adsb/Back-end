package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.MaterialProduto;
import tcatelie.microservice.auth.model.MaterialProdutoId;

public interface MaterialProdutoRepository extends JpaRepository<MaterialProduto, MaterialProdutoId> {

    Page<MaterialProduto> findByMaterial_IdMaterial(Integer idMaterial, Pageable pageable);
}
