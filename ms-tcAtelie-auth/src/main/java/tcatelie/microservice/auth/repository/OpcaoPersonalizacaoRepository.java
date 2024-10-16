package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.OpcaoPersonalizacao;

public interface OpcaoPersonalizacaoRepository extends JpaRepository<OpcaoPersonalizacao, Integer> {
}
