package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
