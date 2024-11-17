package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.PersonalizacaoItemPedido;

public interface PersonalizacaoItemPedidoRepository extends JpaRepository<PersonalizacaoItemPedido, Integer> {
}
