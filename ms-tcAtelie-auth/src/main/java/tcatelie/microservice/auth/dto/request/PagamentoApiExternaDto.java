package tcatelie.microservice.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagamentoApiExternaDto {

    @JsonProperty("name")
    private String nome;
    @JsonProperty("payment_type_id")
    private String tipoPagamento;
    @JsonProperty("status")
    private String status;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
