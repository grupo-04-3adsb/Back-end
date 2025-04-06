package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcatelie.microservice.auth.model.Faq;

public interface FAQRepository extends JpaRepository<Faq, Long> {
}
