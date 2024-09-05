package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tcatelie.microservice.auth.model.UsuarioClient;

@Repository
public interface UsuarioClientRepository extends JpaRepository<UsuarioClient, Integer> {
    UsuarioClient findByEmail(String email);
}
