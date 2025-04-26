package tcatelie.microservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.kpi.ChartDTO;
import tcatelie.microservice.auth.enums.Periodo;
import tcatelie.microservice.auth.service.KPIsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/kpis")
@RequiredArgsConstructor
public class KPIsController {

  private final KPIsService kpisService;

  @GetMapping("/vendas/periodo")
  public ChartDTO gerarDadosGraficoVendasPeriodo(
          @RequestParam Periodo periodo
  ) {
    return kpisService.gerarDadosGraficoVendasPeriodo(periodo);
  }

  @GetMapping("/setor/status-pedido")
  public ChartDTO gerarDadosGraficoSetorStatusPedido(
          @RequestParam LocalDate inicio,
          @RequestParam LocalDate fim
  ) {
    if (fim.isBefore(inicio)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data final não pode ser antes da data inicial.");
    }

    LocalDateTime inicioDateTime = inicio.atTime(LocalTime.MIN);
    LocalDateTime fimDateTime = fim.atTime(LocalTime.MAX);

    return kpisService.gerarDadosGraficoSetorStatusPedido(inicioDateTime, fimDateTime);
  }

}

