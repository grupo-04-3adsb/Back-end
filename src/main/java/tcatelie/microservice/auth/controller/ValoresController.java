package tcatelie.microservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tcatelie.microservice.auth.dto.response.ValoresResponseDTO;
import tcatelie.microservice.auth.service.ValoresService;

@RestController
@RequestMapping("/valores")
@RequiredArgsConstructor
public class ValoresController {

  private final ValoresService valoresService;

  @GetMapping
  public Page<ValoresResponseDTO> buscaValoresPaginado(
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "size", defaultValue = "10") int size,
          @RequestParam(name = "sort", defaultValue = "ASC") String sort,
          @RequestParam(name = "sortField", defaultValue = "id") String sortField
  ) {
    return valoresService.buscaValoresPaginado(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), sortField)));
  }
}

