package tcatelie.microservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tcatelie.microservice.auth.dto.response.DepoimentosResponseDTO;
import tcatelie.microservice.auth.service.DepoimentoService;

@RestController
@RequestMapping("/depoimentos")
@RequiredArgsConstructor
public class DepoimentoController {

  private final DepoimentoService depoimentoService;

  @GetMapping
  public Page<DepoimentosResponseDTO> buscaPaginada(
          @RequestParam(name = "page", defaultValue = "0") Integer page,
          @RequestParam(name = "size", defaultValue = "10") Integer size,
          @RequestParam(name = "sort", defaultValue = "ASC") String sort,
          @RequestParam(name = "sortField", defaultValue = "id") String sortField
  ){
    return depoimentoService.buscaPaginada(PageRequest.of(page, size, Sort.Direction.valueOf(sort), sortField));
  }
}
