package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.model.CustoOutros;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.model.ParametroGeral;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.repository.CustosOutrosRepository;
import tcatelie.microservice.auth.repository.ParametroGeralRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalculaPrecoService {

  private final CustosOutrosRepository custosOutrosRepository;
  private final ParametroGeralRepository parametroGeralRepository;

  private static final Logger logger = LoggerFactory.getLogger(CalculaPrecoService.class);

  private ParametroGeral buscaParametroProjecaoVendas() {
    return Optional.ofNullable(parametroGeralRepository.findById("PROJECAO_VENDAS").get())
            .filter(param -> isValorValido(param))
            .orElseThrow(() -> {
              logger.error("Parâmetro PROJECAO_VENDAS inválido ou não encontrado.");
              return new IllegalArgumentException("Parâmetro PROJECAO_VENDAS inválido ou não encontrado.");
            });
  }

  private boolean isValorValido(ParametroGeral parametro) {
    String valor = parametro.getValor();
    if (valor == null || !isConvertibleToDouble(valor)) {
      logger.error("Valor do parâmetro PROJECAO_VENDAS é inválido: " + valor);
      return false;
    }
    return true;
  }

  private boolean isConvertibleToDouble(String valor) {
    try {
      Double.parseDouble(valor);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public Double valorCustoOutrosDiluidoParametroEditado(List<CustoOutros> custosOutros, ParametroGeral parametroGeralEditado) {
    return custosOutros.stream().mapToDouble(CustoOutros::getValor).sum() / Double.parseDouble(parametroGeralEditado.getValor());
  }

  public Double valorCustoOutrosDiluido(List<CustoOutros> custosOutros) {
    return custosOutros.stream().mapToDouble(CustoOutros::getValor).sum() / Double.parseDouble(buscaParametroProjecaoVendas().getValor());
  }

  public Double calcularPrecoProduto(Produto produto) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutrosRepository.findAll());

    double totalProducao = valorProducao + valorCustosOutros;
    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;

    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoProdutoProjecaoVendaEditada(Produto produto, ParametroGeral parametroGeralEditado) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = valorCustoOutrosDiluidoParametroEditado(custosOutrosRepository.findAll(), parametroGeralEditado);

    double totalProducao = valorProducao + valorCustosOutros;
    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;

    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoNovoCustoOutro(Produto produto, double novoCustoOutro) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    List<CustoOutros> custosOutros = custosOutrosRepository.findAll();
    custosOutros.add(new CustoOutros(novoCustoOutro));

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutros);

    double totalProducao = valorProducao + valorCustosOutros;

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoMargemLucro(Produto produto, double margemLucro) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutrosRepository.findAll());

    double totalProducao = valorProducao + valorCustosOutros;

    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoMargemLucroCustoOutro(Produto produto, double margemLucro, double novoCustoOutro) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    List<CustoOutros> custosOutros = custosOutrosRepository.findAll();
    custosOutros.add(new CustoOutros(novoCustoOutro));

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutros);

    double totalProducao = valorProducao + valorCustosOutros;

    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularSemCustoOutro(Produto produto, CustoOutros custoRemovido) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutrosRepository.findAll().stream()
            .filter(custoOutros -> !custoOutros.getIdCustoOutros().equals(custoRemovido.getIdCustoOutros())).toList());

    double totalProducao = valorProducao + valorCustosOutros;

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoMargemLucroSemCustoOutro(Produto produto, double margemLucro, CustoOutros custoRemovido) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutrosRepository.findAll().stream()
            .filter(custoOutros -> !custoOutros.getIdCustoOutros().equals(custoRemovido.getIdCustoOutros())).toList());

    double totalProducao = valorProducao + valorCustosOutros;

    return totalProducao * (1 + produto.getMargemLucro() / 100);
  }

  public Double calcularPrecoComCustoOutroEditado(Produto produto, CustoOutros custoEditado) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum();

    List<CustoOutros> custosOutros = custosOutrosRepository.findAll().stream().filter(custoOutros -> !custoOutros.getIdCustoOutros().equals(custoEditado.getIdCustoOutros())).toList();
    custosOutros.add(custoEditado);
    double valorCustosOutros = valorCustoOutrosDiluido(custosOutros);

    double totalProducao = valorProducao + valorCustosOutros;

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoComMaterialNovo(Produto produto, double novoPrecoMaterial, double qtdMaterial) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario()).sum() + novoPrecoMaterial * qtdMaterial;

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutrosRepository.findAll());

    double totalProducao = valorProducao + valorCustosOutros;

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoComMaterialEditado(Produto produto, Integer idMaterial, double novoPrecoMaterial, Integer qtdMaterial) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> {
              if (material.getMaterial().getIdMaterial().equals(idMaterial)) {
                return novoPrecoMaterial * qtdMaterial;
              }
              return material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario();
            }).sum();

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutrosRepository.findAll());

    double totalProducao = valorProducao + valorCustosOutros;

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }

  public Double calcularPrecoComMaterialRemovido(Produto produto, Integer idMaterial) {
    double valorProducao = produto.getMateriaisProduto().stream()
            .mapToDouble(material -> {
              if (material.getMaterial().getIdMaterial().equals(idMaterial)) {
                return 0;
              }
              return material.getMaterial().getPrecoUnitario() * material.getQtdMaterialNecessario();
            }).sum();

    double valorCustosOutros = valorCustoOutrosDiluido(custosOutrosRepository.findAll());

    double totalProducao = valorProducao + valorCustosOutros;

    double margemLucro = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
    return totalProducao * (1 + margemLucro / 100);
  }
}
