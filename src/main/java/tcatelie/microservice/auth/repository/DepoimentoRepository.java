package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Depoimento;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {
}
