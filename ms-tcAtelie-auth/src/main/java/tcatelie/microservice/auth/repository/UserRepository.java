package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import tcatelie.microservice.auth.model.Usuario;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Integer>{

    Optional<UserDetails> findByEmail(String email);

    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    boolean existsByEmail(String email);
}
