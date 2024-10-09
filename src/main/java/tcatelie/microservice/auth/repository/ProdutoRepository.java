package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
}
