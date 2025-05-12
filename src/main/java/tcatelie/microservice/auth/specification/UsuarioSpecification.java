package tcatelie.microservice.auth.specification;

import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import tcatelie.microservice.auth.dto.filter.UsuarioFiltroDTO;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioSpecification {

  private static final Logger logger = LoggerFactory.getLogger(UsuarioSpecification.class);

  public static Specification<Usuario> getFilterUser(UsuarioFiltroDTO filtroDTO){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filtroDTO.getNome() != null && !filtroDTO.getNome().isEmpty()) {
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("nome")),
                "%" + filtroDTO.getNome().toLowerCase() + "%"
        ));
      }

      if (filtroDTO.getEmail() != null && !filtroDTO.getEmail().isEmpty()) {
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("email")),
                "%" + filtroDTO.getEmail().toLowerCase() + "%"
        ));
      }

      if (filtroDTO.getTelefone() != null && !filtroDTO.getTelefone().isEmpty()) {
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("telefone")),
                "%" + filtroDTO.getTelefone().toLowerCase() + "%"
        ));
      }

      if (filtroDTO.getCpf() != null && !filtroDTO.getCpf().isEmpty()) {
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("cpf")),
                "%" + filtroDTO.getCpf().toLowerCase() + "%"
        ));
      }

      predicates.add(criteriaBuilder.equal(root.get("status"), filtroDTO.isAtivo() ? Status.HABILITADO : Status.BLOQUEADO));

      if (filtroDTO.getRoles() != null && !filtroDTO.getRoles().isEmpty()) {
        predicates.add(root.get("role").in(filtroDTO.getRoles()));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
