package tcatelie.microservice.auth.specification;

import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import tcatelie.microservice.auth.dto.filter.ProdutoFiltroDTO;
import tcatelie.microservice.auth.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoSpecification {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoSpecification.class);

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

            List<Predicate> categoriaSubcategoriaPredicates = new ArrayList<>();

            if (StringUtils.hasText(filtro.getNomeCategoria())) {
                categoriaSubcategoriaPredicates.add(
                        criteriaBuilder.equal(root.get("categoria").get("nomeCategoria"), filtro.getNomeCategoria())
                );
            }

            if (StringUtils.hasText(filtro.getNomeSubcategoria())) {
                categoriaSubcategoriaPredicates.add(
                        criteriaBuilder.equal(root.get("subcategoria").get("nomeSubcategoria"), filtro.getNomeSubcategoria())
                );
            }

            if (!categoriaSubcategoriaPredicates.isEmpty()) {
                predicates.add(criteriaBuilder.or(categoriaSubcategoriaPredicates.toArray(new Predicate[0])));
            }

            if (filtro.getIsPersonalizavel() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("personalizavel"), filtro.getIsPersonalizavel())
                );
            }

            predicates.add(
                    criteriaBuilder.equal(root.get("produtoAtivo"), true)
            );

            logger.info("Predicates: {}", predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
