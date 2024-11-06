package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
