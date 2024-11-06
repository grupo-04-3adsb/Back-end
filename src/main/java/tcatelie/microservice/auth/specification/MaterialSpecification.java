package tcatelie.microservice.auth.specification;

import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import tcatelie.microservice.auth.dto.filter.MaterialFiltroDTO;
import tcatelie.microservice.auth.model.Material;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MaterialSpecification {
    private static final Logger logger = LoggerFactory.getLogger(MaterialSpecification.class);

    public static Specification<Material> filtrar(MaterialFiltroDTO filtro) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filtro.getNomeMaterial())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nomeMaterial")),
                        "%" + filtro.getNomeMaterial().toLowerCase() + "%"
                ));
            }

            if (filtro.getPrecoUnitarioMinimo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("precoUnitario"), filtro.getPrecoUnitarioMinimo()
                ));
            }

            if (filtro.getPrecoUnitarioMaximo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("precoUnitario"), filtro.getPrecoUnitarioMaximo()
                ));
            }

            if (filtro.getDataHoraCadastroInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dthrCadastro"), filtro.getDataHoraCadastroInicio()
                ));
            }
            if (filtro.getDataHoraCadastroFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dthrCadastro"), filtro.getDataHoraCadastroFim()
                ));
            }

            if (filtro.getDataHoraAtualizacaoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dthrAtualizacao"), filtro.getDataHoraAtualizacaoInicio()
                ));
            }
            if (filtro.getDataHoraAtualizacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dthrAtualizacao"), filtro.getDataHoraAtualizacaoFim()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
