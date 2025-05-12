package tcatelie.microservice.auth.specification;

import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import tcatelie.microservice.auth.dto.filter.EnderecoFiltroDTO;
import tcatelie.microservice.auth.model.Endereco;

import java.util.ArrayList;
import java.util.List;

public class EnderecoSpecification {

  private static final Logger logger = LoggerFactory.getLogger(EnderecoSpecification.class);

  public static Specification<Endereco> getFilterEndereco(EnderecoFiltroDTO filtro){

    return (root, query,criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if(StringUtils.isNotBlank(filtro.getCep())){
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("cep")),
                "%" + filtro.getCep().toLowerCase() + "%"
        ));
      }

      if(StringUtils.isNotBlank(filtro.getRua())){
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("rua")),
                "%" + filtro.getRua().toLowerCase() + "%"
        ));
      }

      if(StringUtils.isNotBlank(filtro.getNumero())){
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("numero")),
                "%" + filtro.getNumero().toLowerCase() + "%"
        ));
      }

      if(StringUtils.isNotBlank(filtro.getComplemento())){
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("complemento")),
                "%" + filtro.getComplemento().toLowerCase() + "%"
        ));
      }

      if(StringUtils.isNotBlank(filtro.getBairro())){
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("bairro")),
                "%" + filtro.getBairro().toLowerCase() + "%"
        ));
      }

      if(StringUtils.isNotBlank(filtro.getCidade())){
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("cidade")),
                "%" + filtro.getCidade().toLowerCase() + "%"
        ));
      }

      if(StringUtils.isNotBlank(filtro.getEstado())){
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("estado")),
                "%" + filtro.getEstado().toLowerCase() + "%"
        ));
      }

      if(filtro.getIdUsuario() != null){
        predicates.add(criteriaBuilder.equal(
                root.get("usuario").get("idUsuario"),
                filtro.getIdUsuario()
        ));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
