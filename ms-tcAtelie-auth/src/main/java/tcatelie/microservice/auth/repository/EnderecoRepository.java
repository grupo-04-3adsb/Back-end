package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    Optional<Endereco> findByUsuarioIdUsuarioAndCep(Integer idUsuario, String cep);

    List<Endereco> findByUsuarioIdUsuario(Integer idUsuario);
}
