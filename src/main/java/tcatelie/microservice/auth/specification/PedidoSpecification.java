package tcatelie.microservice.auth.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tcatelie.microservice.auth.dto.filter.PedidoFiltroDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.model.ResponsavelPedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoSpecification {

    public static Specification<Pedido> filterBy(PedidoFiltroDTO filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getIdPedido() != null) {
                predicates.add(cb.equal(root.get("id"), filtro.getIdPedido()));
            }
            if (StringUtils.hasText(filtro.getNomeCliente())) {
                predicates.add(cb.like(cb.lower(root.get("nomeUsuario")), "%" + filtro.getNomeCliente().toLowerCase() + "%"));
            }
            if (filtro.getIdCliente() != null) {
                predicates.add(cb.equal(root.get("usuario").get("id"), filtro.getIdCliente()));
            }
            if (filtro.getDataPedidoInicio() != null && filtro.getDataPedidoFim() != null) {
                predicates.add(cb.between(root.get("dataPedido"), filtro.getDataPedidoInicio(), filtro.getDataPedidoFim()));
            }
            if (filtro.getDataEntregaInicio() != null && filtro.getDataEntregaFim() != null) {
                predicates.add(cb.between(root.get("dataEntrega"), filtro.getDataEntregaInicio(), filtro.getDataEntregaFim()));
            }
            if (filtro.getDataPagamentoInicio() != null && filtro.getDataPagamentoFim() != null) {
                predicates.add(cb.between(root.get("dataPagamento"), filtro.getDataPagamentoInicio(), filtro.getDataPagamentoFim()));
            }
            if (filtro.getIdsResponsaveis() != null && !filtro.getIdsResponsaveis().isEmpty()) {
                Join<Pedido, ResponsavelPedido> responsaveisJoin = root.join("responsaveis", JoinType.INNER);
                predicates.add(responsaveisJoin.get("responsavel").get("id").in(filtro.getIdsResponsaveis()));
            }
            if (filtro.getStatusList() != null && !filtro.getStatusList().isEmpty()) {
                predicates.add(root.get("status").in(filtro.getStatusList()));
            }
            if (filtro.getValorTotalMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("valorTotal"), filtro.getValorTotalMin()));
            }
            if (filtro.getValorTotalMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("valorTotal"), filtro.getValorTotalMax()));
            }
            if (filtro.getValorDescontoMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("valorDesconto"), filtro.getValorDescontoMin()));
            }
            if (filtro.getValorDescontoMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("valorDesconto"), filtro.getValorDescontoMax()));
            }
            if (filtro.getValorFreteMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("valorFrete"), filtro.getValorFreteMin()));
            }
            if (filtro.getValorFreteMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("valorFrete"), filtro.getValorFreteMax()));
            }
            if (filtro.getParcelasMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("parcelas"), filtro.getParcelasMin()));
            }
            if (filtro.getParcelasMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("parcelas"), filtro.getParcelasMax()));
            }
            if (filtro.getValorParcelaMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("valorParcela"), filtro.getValorParcelaMin()));
            }
            if (filtro.getValorParcelaMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("valorParcela"), filtro.getValorParcelaMax()));
            }
            if (StringUtils.hasText(filtro.getFormaPgto())) {
                predicates.add(cb.equal(root.get("formaPgto"), filtro.getFormaPgto()));
            }
            if (StringUtils.hasText(filtro.getObservacao())) {
                predicates.add(cb.like(cb.lower(root.get("observacao")), "%" + filtro.getObservacao().toLowerCase() + "%"));
            }
            if (filtro.getDataCancelamentoInicio() != null && filtro.getDataCancelamentoFim() != null) {
                predicates.add(cb.between(root.get("dataCancelamento"), filtro.getDataCancelamentoInicio(), filtro.getDataCancelamentoFim()));
            }
            if (filtro.getDataAtualizacaoInicio() != null && filtro.getDataAtualizacaoFim() != null) {
                predicates.add(cb.between(root.get("dataAtualizacao"), filtro.getDataAtualizacaoInicio(), filtro.getDataAtualizacaoFim()));
            }
            if (filtro.getDataInicioConclusao() != null && filtro.getDataFimConclusao() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), "CONCLUIDO");
                Predicate dataConclusaoPredicate = cb.between(root.get("dataConclusao"), filtro.getDataInicioConclusao(), filtro.getDataFimConclusao());

                predicates.add(cb.and(statusPredicate, dataConclusaoPredicate));
            }
            if (StringUtils.hasText(filtro.getPaymentId())) {
                predicates.add(cb.equal(root.get("paymentId"), filtro.getPaymentId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
