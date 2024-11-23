package tcatelie.microservice.auth.specification;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import tcatelie.microservice.auth.dto.filter.SubcategoriaFiltroDTO;
import tcatelie.microservice.auth.model.Subcategoria;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SubcategoriaSpecification {

    private static final Logger logger = LoggerFactory.getLogger(SubcategoriaSpecification.class);

    public static Specification<Subcategoria> filtrar(SubcategoriaFiltroDTO filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getNomeSubcategoria() != null && !filtro.getNomeSubcategoria().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nomeSubcategoria")),
                        "%" + filtro.getNomeSubcategoria().toLowerCase() + "%"
                ));
            }

            if (filtro.getDescricaoSubcategoria() != null && !filtro.getDescricaoSubcategoria().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("descricaoSubcategoria")),
                        "%" + filtro.getDescricaoSubcategoria().toLowerCase() + "%"
                ));
            }

            if (filtro.getQuantidadeMinimaProdutos() != null || filtro.getQuantidadeMaximaProdutos() != null) {
                var produtosJoin = root.join("produtos");
                query.groupBy(root.get("idSubcategoria"));

                Predicate havingPredicate = criteriaBuilder.conjunction();

                if (filtro.getQuantidadeMinimaProdutos() != null) {
                    havingPredicate = criteriaBuilder.and(havingPredicate,
                            criteriaBuilder.ge(criteriaBuilder.count(produtosJoin), filtro.getQuantidadeMinimaProdutos()));
                }

                if (filtro.getQuantidadeMaximaProdutos() != null) {
                    havingPredicate = criteriaBuilder.and(havingPredicate,
                            criteriaBuilder.le(criteriaBuilder.count(produtosJoin), filtro.getQuantidadeMaximaProdutos()));
                }

                query.having(havingPredicate);
            }


            if (!StringUtils.isEmpty(filtro.getNomeCategoria())) {
                var categoriaJoin = root.join("categoria");
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(categoriaJoin.get("nomeCategoria")),
                        "%" + filtro.getNomeCategoria().toLowerCase() + "%"
                ));
            }


            if (filtro.getDataCadastroInicio() != null && filtro.getDataCadastroFim() != null) {
                predicates.add(criteriaBuilder.between(
                        root.get("dthrCadastro"),
                        filtro.getDataCadastroInicio(),
                        filtro.getDataCadastroFim()
                ));
            } else if (filtro.getDataCadastroInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dthrCadastro"),
                        filtro.getDataCadastroInicio()
                ));
            } else if (filtro.getDataCadastroFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dthrCadastro"),
                        filtro.getDataCadastroFim()
                ));
            }

            if (filtro.getDataAtualizacaoInicio() != null && filtro.getDataAtualizacaoFim() != null) {
                predicates.add(criteriaBuilder.between(
                        root.get("dthrAtualizacao"),
                        filtro.getDataAtualizacaoInicio(),
                        filtro.getDataAtualizacaoFim()
                ));
            } else if (filtro.getDataAtualizacaoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dthrAtualizacao"),
                        filtro.getDataAtualizacaoInicio()
                ));
            } else if (filtro.getDataAtualizacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dthrAtualizacao"),
                        filtro.getDataAtualizacaoFim()
                ));
            }

            predicates.add(criteriaBuilder.equal(root.get("subcategoriaAtiva"), true));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
