package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.enums.OrigemPedido;
import tcatelie.microservice.auth.enums.UserRole;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {

    @Schema(description = "Id do pedido", example = "1")
    private Integer idPedido;

    @Schema(description = "Status da situação do pedido", example = "PENDENTE")
    private String statusPedido;

    @Schema(description = "Status do pedido", example = "Concluído")
    private boolean concluido;

    @Schema(description = "Ids dos responsáveis", example = "[1, 2, 3]")
    private List<Integer> idsResponsaveis;

    @Schema(description = "Data de quando o pedido foi realizado", example = "18/11/2024")
    private String dataPedido;

    @Schema(name = "nome cliente", example = "Clara")
    private String cliente;

    @Schema(description = "Valor do frete do pedido", example = "100.00")
    private Double valorFrete;

    @Schema(description = "Código de rastreio do pedido", example = "123456")
    private String codigoRastreio;

    @Schema(description = "Tempo de entrega do pedido", example = "10")
    private Integer tempoEntrega;

    @Schema(description = "Telefone do cliente", example = "11999999999")
    private String telefoneCliente;

    @Schema(description = "E-mail do cliente", example = "teste@gmail")
    private String emailCliente;

    @Schema(description = "Data de venda do pedido", example = "18/11/2024")
    private String dataVenda;

    @Schema(description = "Data de conclusão")
    private LocalDate dataConclusao;

    @Schema(description = "Forma de pagamento", example = "Pix")
    private String formaPgto;

    @Schema(name = "Itens no pedido")
    private List<ItemPedidoRequestDTO> itens;

    @Schema(name = "Endereço de entrega", implementation = EnderecoRequestDTO.class)
    private EnderecoRequestDTO enderecoEntrega;

    @Schema(name = "Origem do pedido", implementation = OrigemPedido.class)
    private OrigemPedido origemPedido;

    @Schema(name = "Observação")
    private String observacao;

    @Schema(name = "Tipo usuário")
    private UserRole tipoUsuario;

    @Schema(name = "fk id cliente")
    private Integer idCliente;

    @Schema(name = "prazo entrega")
    private Integer prazoEntrega;
}
