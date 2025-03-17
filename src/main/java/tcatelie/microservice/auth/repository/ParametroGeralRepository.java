package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcatelie.microservice.auth.model.ParametroGeral;

public interface ParametroGeralRepository extends JpaRepository<ParametroGeral, String> {

  boolean existsByNome(String nome);

}
