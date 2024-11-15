package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.CustoOutros;

public interface CustosOutrosRepository extends JpaRepository<CustoOutros, Integer> {
}
