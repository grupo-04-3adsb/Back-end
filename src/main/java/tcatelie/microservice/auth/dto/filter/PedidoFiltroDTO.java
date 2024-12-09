package tcatelie.microservice.auth.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Filtro de pedido")
public class PedidoFiltroDTO {

    @Schema(description = "Id do pedido", example = "1")
    private Integer idPedido;
    @Schema(description = "Nome do cliente", example = "João")
    private String nomeCliente;
    @Schema(description = "Id do cliente", example = "1")
    private Integer idCliente;
    @Schema(description = "data de pedido início", example = "2021-01-01T00:00:00")
    private LocalDateTime dataPedidoInicio;
    @Schema(description = "data de pedido fim", example = "2021-01-01T00:00:00")
    private LocalDateTime dataPedidoFim;
    @Schema(description = "data de entrega início", example = "2021-01-01T00:00:00")
    private LocalDateTime dataEntregaInicio;
    @Schema(description = "data de entrega fim", example = "2021-01-01T00:00:00")
    private LocalDateTime dataEntregaFim;
    @Schema(description = "data de pagamento início", example = "2021-01-01T00:00:00")
    private LocalDateTime dataPagamentoInicio;
    @Schema(description = "data de pagamento fim", example = "2021-01-01T00:00:00")
    private LocalDateTime dataPagamentoFim;
    @Schema(description = "Id dos responsáveis", example = "[1,2,3]")
    private List<Integer> idsResponsaveis;
    @Schema(description = "Status do pedido", example = "[\"PENDENTE_PAGAMENTO\",\"PENDENTE\"]")
    private List<String> statusList;

    @Schema(description = "Valor total mínimo", example = "10.0")
    private Double valorTotalMin;
    @Schema(description = "Valor total máximo", example = "100.0")
    private Double valorTotalMax;
    @Schema(description = "Valor desconto mínimo", example = "10.0")
    private Double valorDescontoMin;
    @Schema(description = "Valor desconto máximo", example = "100.0")
    private Double valorDescontoMax;
    @Schema(description = "Valor frete mínimo", example = "10.0")
    private Double valorFreteMin;
    @Schema(description = "Valor frete máximo", example = "100.0")
    private Double valorFreteMax;
    @Schema(description = "Valor total com desconto mínimo", example = "10.0")
    private Integer parcelasMin;
    @Schema(description = "Valor total com desconto máximo", example = "100.0")
    private Integer parcelasMax;
    @Schema(description = "Valor parcela mínima", example = "10.0")
    private Double valorParcelaMin;
    @Schema(description = "Valor parcela máxima", example = "100.0")
    private Double valorParcelaMax;

    @Schema(description = "Forma de pagamento", example = "DINHEIRO")
    private String formaPgto;
    @Schema(description = "Observação", example = "Pedido de teste")
    private String observacao;
    @Schema(description = "Data de cancelamento início", example = "2021-01-01T00:00:00")
    private LocalDateTime dataCancelamentoInicio;
    @Schema(description = "Data de cancelamento fim", example = "2021-01-01T00:00:00")
    private LocalDateTime dataCancelamentoFim;
    @Schema(description = "Data de atualização início", example = "2021-01-01T00:00:00")
    private LocalDateTime dataAtualizacaoInicio;
    @Schema(description = "Data de atualização fim", example = "2021-01-01T00:00:00")
    private LocalDateTime dataAtualizacaoFim;
    @Schema(description = "Data de início de conclusão", example = "2021-01-01T00:00:00")
    private LocalDateTime dataInicioConclusao;
    @Schema(description = "Data de fim de conclusão", example = "2021-01-01T00:00:00")
    private LocalDateTime dataFimConclusao;
    @Schema(description = "Id do pagamento", example = "1")
    private String paymentId;
}
