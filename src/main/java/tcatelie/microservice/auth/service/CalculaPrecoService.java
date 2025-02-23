package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.model.CustoOutros;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.repository.CustosOutrosRepository;

@Service
@RequiredArgsConstructor
public class CalculaPrecoService {

  private final CustosOutrosRepository custosOutrosRepository;

  public Double calcularPrecoProduto(Produto produto) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = custosOutrosRepository.findAll().stream()
            .mapToDouble(custoOutros -> custoOutros.getValor()).sum();

    double totalProducao = valorProducao + valorCustosOutros;

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoNovoCustoOutro(Produto produto, double novoCustoOutro) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = custosOutrosRepository.findAll().stream()
            .mapToDouble(custoOutros -> custoOutros.getValor()).sum();

    double totalProducao = valorProducao + valorCustosOutros + novoCustoOutro;

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoMargemLucro(Produto produto, double margemLucro) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = custosOutrosRepository.findAll().stream()
            .mapToDouble(custoOutros -> custoOutros.getValor()).sum();

    double totalProducao = valorProducao + valorCustosOutros;

    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoMargemLucroCustoOutro(Produto produto, double margemLucro, double novoCustoOutro) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = custosOutrosRepository.findAll().stream()
            .mapToDouble(custoOutros -> custoOutros.getValor()).sum();

    double totalProducao = valorProducao + valorCustosOutros + novoCustoOutro;

    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularSemCustoOutro(Produto produto, CustoOutros custoRemovido) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = custosOutrosRepository.findAll().stream()
            .mapToDouble(custoOutros -> custoOutros.getValor()).sum();

    double totalProducao = valorProducao + valorCustosOutros - custoRemovido.getValor();

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoMargemLucroSemCustoOutro(Produto produto, double margemLucro, CustoOutros custoRemovido) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = custosOutrosRepository.findAll().stream()
            .mapToDouble(custoOutros -> custoOutros.getValor()).sum();

    double totalProducao = valorProducao + valorCustosOutros - custoRemovido.getValor();

    return totalProducao * (1 + produto.getMargemLucro() / 100);
  }

  public Double calcularPrecoComCustoOutroEditado(Produto produto, CustoOutros custoEditado) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = custosOutrosRepository.findAll().stream().filter(custoOutros -> !custoOutros.getIdCustoOutros().equals(custoEditado.getIdCustoOutros()))
            .mapToDouble(custoOutros -> custoOutros.getValor()).sum();

    double totalProducao = valorProducao + valorCustosOutros + custoEditado.getValor();

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }
}
