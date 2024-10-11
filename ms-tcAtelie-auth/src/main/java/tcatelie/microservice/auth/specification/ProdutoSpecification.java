package tcatelie.microservice.auth.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import tcatelie.microservice.auth.dto.filter.ProdutoFiltroDTO;
import tcatelie.microservice.auth.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoSpecification {

    public static Specification<Produto> filtrar(ProdutoFiltroDTO filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filtro.getNome())) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")),
                                "%" + filtro.getNome().toLowerCase() + "%")
                );
            }

            if (StringUtils.hasText(filtro.getSku())) {
                predicates.add(
                        criteriaBuilder.equal(root.get("sku"), filtro.getSku())
                );
            }

            if (filtro.getMargemLucroMinima() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("margemLucro"), filtro.getMargemLucroMinima())
                );
            }

            if (filtro.getMargemLucroMaxima() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("margemLucro"), filtro.getMargemLucroMaxima())
                );
            }

            if (filtro.getPrecoMinimo() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), filtro.getPrecoMinimo())
                );
            }

            if (filtro.getPrecoMaximo() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("preco"), filtro.getPrecoMaximo())
                );
            }

            if (filtro.getIdCategoria() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("categoria").get("id"), filtro.getIdCategoria())
                );
            }

            if (filtro.getIdSubcategoria() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("subcategoria").get("id"), filtro.getIdSubcategoria())
                );
            }

            if (filtro.getIsPersonalizavel() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("personalizavel"), filtro.getIsPersonalizavel())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
