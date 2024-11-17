package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>, JpaSpecificationExecutor<Pedido> {

    List<Pedido> findByStatus(StatusPedido status);

    Page<Pedido> findByStatusIn(List<StatusPedido> status, Pageable pageable);

    @Query("SELECT p FROM Pedido p "
            + "JOIN p.responsaveis r "
            + "WHERE (:idPedido IS NULL OR p.id = :idPedido) "
            + "AND (:nomeCliente IS NULL OR p.usuario.nome LIKE %:nomeCliente%) "
            + "AND (:idResponsavel IS NULL OR r.responsavel.idUsuario = :idResponsavel) "
            + "AND ((:statusList IS NULL OR p.status IN :statusList) "
            + "    OR (p.status = :concluidoStatus AND p.dataEntrega BETWEEN :startOfWeek AND :endOfWeek))")
    List<Pedido> findAll(
            @Param("idPedido") Integer idPedido,
            @Param("nomeCliente") String nomeCliente,
            @Param("idResponsavel") Integer idResponsavel,
            @Param("statusList") List<String> statusList,
            @Param("concluidoStatus") String concluidoStatus,
            @Param("startOfWeek") LocalDateTime startOfWeek,
            @Param("endOfWeek") LocalDateTime endOfWeek);

    Optional<Pedido> findByStatusAndUsuario_IdUsuario(StatusPedido status, Integer idUsuario);
}
