package tcatelie.microservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tcatelie.microservice.auth.dto.response.PaginaInfoResponseDTO;
import tcatelie.microservice.auth.service.PaginaInfoService;

@RestController
@RequestMapping("/pagina-infos")
@RequiredArgsConstructor
public class PaginaInfoController {

  private final PaginaInfoService paginaInfoService;

  @GetMapping
  public Page<PaginaInfoResponseDTO> buscaPaginasFiltradas(
          @RequestParam(name = "destino", required = false) String destino,
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "size", defaultValue = "10") int size,
          @RequestParam(name = "sort", defaultValue = "dataCriacao") String sort,
          @RequestParam(name = "direction", defaultValue = "ASC") String direction) {

    return paginaInfoService.buscaPaginasFiltradas(destino, PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));
  }
}