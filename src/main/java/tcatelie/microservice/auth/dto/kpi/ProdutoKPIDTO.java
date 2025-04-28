package tcatelie.microservice.auth.dto.kpi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcatelie.microservice.auth.util.DateFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoKPIDTO {

  private Integer id;
  private String nomeProduto;
  private String categoria;
  private String subcategoria;
  private Integer qtdItensVendidos;
  private String dataCadadastro;
  private String urlImagemPrincipal;

  public ProdutoKPIDTO (Integer id, String nomeProduto, String categoria, String subcategoria, Long qtdItensVendidos, LocalDateTime dataCadastro, String urlImagemPrincipal) {
    this.id = id;
    this.nomeProduto = nomeProduto;
    this.categoria = categoria;
    this.subcategoria = subcategoria;
    this.qtdItensVendidos = qtdItensVendidos.intValue();
    this.dataCadadastro = DateFormat.format(dataCadastro);
    this.urlImagemPrincipal = urlImagemPrincipal;
  }
}
