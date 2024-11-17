package tcatelie.microservice.auth.enums;

public enum StatusPedido {
    CANCELADO("Cancelado"),
    CARRINHO("Carrinho"),
    PENDENTE_PAGAMENTO("Pendente de pagamento"),
    PENDENTE("Pendente"),
    EM_PREPARO("Em preparo"),
    EM_ROTA("Em rota"),
    CONCLUIDO("Concluído");

    private String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusPedido fromDescricao(String descricao) {
        for (StatusPedido status : StatusPedido.values()) {
            if (status.descricao.equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + descricao);
    }
}
