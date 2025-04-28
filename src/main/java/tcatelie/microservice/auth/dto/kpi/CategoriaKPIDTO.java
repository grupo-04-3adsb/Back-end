package tcatelie.microservice.auth.dto.kpi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoriaKPIDTO {

  private String nomeCategoria;
  private Integer quantidadeVendas;
  private Double porcentagemTotalQtdVendas;
  private String cor;

  public CategoriaKPIDTO(String nomeCategoria, Long quantidadeVendas, Double porcentagemTotalQtdVendas, String cor) {
    this.nomeCategoria = nomeCategoria;
    this.quantidadeVendas = quantidadeVendas.intValue();
    this.porcentagemTotalQtdVendas = porcentagemTotalQtdVendas;
    this.cor = cor;
  }
}
