package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.ImagensProduto;

public interface ImagensProdutoRepository extends JpaRepository<ImagensProduto, Integer> {
}
