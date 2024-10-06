package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
}
