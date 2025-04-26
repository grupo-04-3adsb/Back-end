package tcatelie.microservice.auth.dto.kpi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaKPIDTO {

  private String nomeCategoria;
  private Integer quantidadeVendas;
  private Double porcentagemTotalQtdVendas;
  private String cor;
}
