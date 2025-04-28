package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.kpi.*;
import tcatelie.microservice.auth.enums.Periodo;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.model.ParametroGeral;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.CategoriaRepository;
import tcatelie.microservice.auth.repository.PedidoRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KPIsService {

  private final PedidoRepository pedidoRepository;

  private final ProdutoRepository produtoRepository;

  private final ParametroGeralService parametroGeralService;

  private final CategoriaRepository categoriaRepository;

  public ChartDTO gerarDadosGraficoVendasPeriodo(
          Periodo periodo
  ) {
    Map<String, LocalDateTime[]> periodos = new LinkedHashMap<>();
    List<String> labels = new ArrayList<>();

    ChartDTO grafico = new ChartDTO();
    grafico.setDatasets(new ArrayList<>());

    if (Periodo.MENSAL.equals(periodo)) {
      for (int i = 0; i < 12; i++) {
        Month mes = Month.of(i + 1);

        String nomeMes = mes.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

        labels.add(nomeMes);
        LocalDateTime inicio = LocalDateTime.of(
                LocalDate.of(LocalDate.now().getYear(), mes, 1),
                LocalTime.MIN
        );
        LocalDateTime fim = LocalDateTime.of(
                LocalDate.of(LocalDate.now().getYear(), mes, mes.length(LocalDate.now().isLeapYear())),
                LocalTime.MAX
        );

        periodos.put(nomeMes, new LocalDateTime[]{inicio, fim});
      }
    } else if (Periodo.SEMANAL.equals(periodo)) {
      LocalDate dataInicioMes = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
      LocalDate dataFimMes = dataInicioMes.withDayOfMonth(dataInicioMes.lengthOfMonth());

      LocalDate inicioSemana = dataInicioMes;
      int contadorSemana = 1;

      while (!inicioSemana.isAfter(dataFimMes)) {
        LocalDate fimSemana = inicioSemana.plusDays(6);
        if (fimSemana.isAfter(dataFimMes)) {
          fimSemana = dataFimMes;
        }

        labels.add("Semana %s: %s a %s".formatted(
                contadorSemana,
                inicioSemana.getDayOfMonth() + "/" + inicioSemana.getMonthValue(),
                fimSemana.getDayOfMonth() + "/" + fimSemana.getMonthValue()
        ));

        periodos.put(
                "Semana %s: %s a %s".formatted(
                        contadorSemana,
                        inicioSemana.getDayOfMonth() + "/" + inicioSemana.getMonthValue(),
                        fimSemana.getDayOfMonth() + "/" + fimSemana.getMonthValue()
                ),
                new LocalDateTime[]{
                        LocalDateTime.of(inicioSemana, LocalTime.MIN),
                        LocalDateTime.of(fimSemana, LocalTime.MAX)
                }
        );

        inicioSemana = fimSemana.plusDays(1);
        contadorSemana++;
      }
    } else if (Periodo.ANUAL.equals(periodo)) {
      int anoAtual = LocalDate.now().getYear();
      int quantidadeAnos = 5;

      for (int i = 0; i < quantidadeAnos; i++) {
        int ano = anoAtual - (quantidadeAnos - 1 - i);

        String nomeAno = String.valueOf(ano);

        labels.add(nomeAno);

        LocalDateTime inicio = LocalDateTime.of(
                LocalDate.of(ano, Month.JANUARY, 1),
                LocalTime.MIN
        );
        LocalDateTime fim = LocalDateTime.of(
                LocalDate.of(ano, Month.DECEMBER, 31),
                LocalTime.MAX
        );

        periodos.put(nomeAno, new LocalDateTime[]{inicio, fim});
      }
    }

    grafico.setLabels(labels);
    List<DatasetDTO> datasets = new ArrayList<>();

    periodos.forEach((key, value) -> {
      DatasetDTO<Double> dataset = new DatasetDTO();
      dataset.setLabel(key);

      List<Pedido> pedidos = pedidoRepository.findByStatusAndDataConclusaoBetween(StatusPedido.CONCLUIDO, value[0], value[1]);
      Double totalVendas = pedidos.stream()
              .mapToDouble(Pedido::getValorTotal)
              .sum();

      dataset.setData(List.of(totalVendas));
      grafico.getDatasets().add(dataset);
    });

    return grafico;
  }

  public ChartDTO gerarDadosGraficoSetorStatusPedido(LocalDateTime dataInicio, LocalDateTime dataFim) {
    ChartDTO grafico = new ChartDTO();
    grafico.setDatasets(new ArrayList<>());

    List<String> labels = new ArrayList<>();
    List<DatasetDTO> datasets = new ArrayList<>();

    List<StatusPedido> statusList = List.of(StatusPedido.CONCLUIDO,
            StatusPedido.EM_PREPARO,
            StatusPedido.EM_ROTA,
            StatusPedido.PENDENTE,
            StatusPedido.CANCELADO);

    for (StatusPedido status : statusList) {
      labels.add(status.getDescricao());
      DatasetDTO<Integer> dataset = new DatasetDTO();
      dataset.setLabel(status.getDescricao());

      Integer quantidade = pedidoRepository.countByStatusAndDataPedidoBetween(status, dataInicio, dataFim);

      dataset.setData(List.of(quantidade));
      datasets.add(dataset);
    }

    grafico.setLabels(labels);
    grafico.setDatasets(datasets);

    return grafico;
  }

  public ProjecaoVendaDTO getProjecaoVendaPorcentagemAtingida(){
    LocalDateTime inicioMes = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0);
    LocalDateTime fimMes = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().lengthOfMonth(), 23, 59, 59);

    Integer qtdPedidosConcluidos = pedidoRepository.countByStatusAndDataConclusaoBetween(StatusPedido.CONCLUIDO, inicioMes, fimMes);

    ParametroGeral parametroGeral = parametroGeralService.findByName("PROJECAO_VENDAS");

    Double valorProjecao = Double.valueOf(parametroGeral.getValor());

    Double porcentagemAtingida = (qtdPedidosConcluidos / valorProjecao) * 100;

    ProjecaoVendaDTO projecaoVenda = new ProjecaoVendaDTO();
    projecaoVenda.setExpectativa(valorProjecao.intValue());
    projecaoVenda.setPorcentagem(porcentagemAtingida);
    projecaoVenda.setQuantidade(qtdPedidosConcluidos);

    return projecaoVenda;
  }

  public CategoriaKPIDTO buscarCategoriaMaisVendida() {
    Integer qtdPedidosConcluidos = pedidoRepository.countByStatus(StatusPedido.CONCLUIDO);
    CategoriaKPIDTO kpi = categoriaRepository.buscarCategoriaMaisVendida(PageRequest.of(0, 1)).getContent().get(0);

    if(kpi == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma categoria encontrada.");
    }

    Double porcentagem = (kpi.getQuantidadeVendas() / (double) qtdPedidosConcluidos) * 100;
    kpi.setPorcentagemTotalQtdVendas(porcentagem);
    return kpi;
  }

  public Page<ProdutoKPIDTO> buscarProdutosMaisVendidos(
          Pageable page
  ) {
    return produtoRepository.buscarProdutosMaisVendidos(StatusPedido.CONCLUIDO, page);
  }
}
