package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tcatelie.microservice.auth.dto.filter.CustoOutrosFiltroDTO;
import tcatelie.microservice.auth.model.CustoOutros;

public interface CustosOutrosRepository extends JpaRepository<CustoOutros, Integer> {

    @Query("SELECT c FROM CustoOutros c WHERE " +
            "(:#{#filtro.descricao} IS NULL OR c.descricao LIKE %:#{#filtro.descricao}%) AND " +
            "(:#{#filtro.valorMin} IS NULL OR c.valor >= :#{#filtro.valorMin}) AND " +
            "(:#{#filtro.valorMax} IS NULL OR c.valor <= :#{#filtro.valorMax}) AND " +
            "(:#{#filtro.dataHoraAtualizacao} IS NULL OR c.dataHoraAtualizacao = :#{#filtro.dataHoraAtualizacao}) AND " +
            "(:#{#filtro.dataHoraCriacao} IS NULL OR c.dataHoraCriacao = :#{#filtro.dataHoraCriacao})"
    )
    Page<CustoOutros> findAll(CustoOutrosFiltroDTO filtro, Pageable page);
}
