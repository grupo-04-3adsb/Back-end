package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Banner;

public interface BannersRepository extends JpaRepository<Banner, Long> {
}
