package tcatelie.microservice.auth.specification;

import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import tcatelie.microservice.auth.dto.filter.CategoriaFiltroDTO;
import tcatelie.microservice.auth.model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaSpecification {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaSpecification.class);

    public static Specification<Categoria> filtrar(CategoriaFiltroDTO filtro){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getNomeCategoria() != null && !filtro.getNomeCategoria().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nomeCategoria")),
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

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
