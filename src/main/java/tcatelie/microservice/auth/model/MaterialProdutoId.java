package tcatelie.microservice.auth.model;

import java.io.Serializable;
import java.util.Objects;

public class MaterialProdutoId implements Serializable {

    private Integer material;
    private Integer produto;

    public MaterialProdutoId() {
    }

    public MaterialProdutoId(Integer material, Integer produto) {
        this.material = material;
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialProdutoId)) return false;
        MaterialProdutoId that = (MaterialProdutoId) o;
        return Objects.equals(material, that.material) && Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, produto);
    }
}
