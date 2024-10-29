package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Personalizacao;

public interface PersonalizacaoRepository extends JpaRepository<Personalizacao, Integer> {
}
