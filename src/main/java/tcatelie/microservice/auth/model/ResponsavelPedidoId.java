package tcatelie.microservice.auth.model;

import java.io.Serializable;
import java.util.Objects;

public class ResponsavelPedidoId implements Serializable {

    private Integer responsavel;
    private Integer pedido;

    public ResponsavelPedidoId() {
    }

    public ResponsavelPedidoId(Integer responsavel, Integer pedido) {
        this.responsavel = responsavel;
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponsavelPedidoId)) return false;
        ResponsavelPedidoId that = (ResponsavelPedidoId) o;
        return Objects.equals(responsavel, that.responsavel) && Objects.equals(pedido, that.pedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responsavel, pedido);
    }
}
