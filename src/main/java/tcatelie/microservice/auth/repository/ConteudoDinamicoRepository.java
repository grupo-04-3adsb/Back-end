package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.ConteudoDinamico;

public interface ConteudoDinamicoRepository extends JpaRepository<ConteudoDinamico, Long> {
}
