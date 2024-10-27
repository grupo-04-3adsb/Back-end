package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.MaterialProduto;
import tcatelie.microservice.auth.model.MaterialProdutoId;

public interface MaterialProdutoRepository extends JpaRepository<MaterialProduto, MaterialProdutoId> {
}
