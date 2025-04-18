package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.PaginaInfo;

public interface PaginaInfoRepository extends JpaRepository<PaginaInfo, Long> {

  Page<PaginaInfo> findByDestino(String destino, Pageable page);
}
