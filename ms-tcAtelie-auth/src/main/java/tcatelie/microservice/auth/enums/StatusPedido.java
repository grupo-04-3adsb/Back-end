package tcatelie.microservice.auth.enums;

public enum StatusPedido {
    CARRINHO("Carrinho"),
    PENDENTE_PAGAMENTO("Pendente de pagamento"),
    PENDENTE("Pendente"),
    EM_PREPARO("Em preparo"),
    EM_ROTA("Em rota"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
