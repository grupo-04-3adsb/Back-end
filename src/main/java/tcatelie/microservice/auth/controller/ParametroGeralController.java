package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.ParametroGeralRequestDTO;
import tcatelie.microservice.auth.dto.response.ParametroGeralResponseDTO;
import tcatelie.microservice.auth.mapper.ParametroGeralMapper;
import tcatelie.microservice.auth.service.ParametroGeralService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parametros-gerais")
public class ParametroGeralController {

  private final ParametroGeralService service;

  @Operation(summary = "Cadastro de parâmetro geral",
          description = "Cadastra um novo parâmetro geral no sistema",
          tags = {"Parametro Geral"}
  )
  @PostMapping
  public void save(@RequestBody ParametroGeralRequestDTO parametroGeral) {
    service.save(parametroGeral);
  }

  @Operation(summary = "Listagem de parâmetros gerais",
          description = "Lista todos os parâmetros gerais cadastrados no sistema",
          tags = {"Parametro Geral"}
  )
  @GetMapping
  public Page<ParametroGeralResponseDTO> findAll(
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "10") Integer size,
          @RequestParam(value = "sortBy", defaultValue = "nome") String sortBy,
          @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder
  ) {
    return service.findAll(
            PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sortOrder.toUpperCase()), sortBy))
    );
  }

  @Operation(summary = "Atualização de parâmetro geral",
          description = "Atualiza um parâmetro geral no sistema",
          tags = {"Parametro Geral"}
  )
  @PutMapping
  public void update(@RequestBody ParametroGeralRequestDTO parametroGeral) {
    service.update(parametroGeral);
  }

  @Operation(summary = "Exclusão de parâmetro geral",
          description = "Exclui um parâmetro geral no sistema",
          tags = {"Parametro Geral"}
  )
  @DeleteMapping("/{name}")
  public void delete(@PathVariable String name) {
    service.delete(name);
  }

  @GetMapping("/{name}")
  public ParametroGeralResponseDTO findByName(@PathVariable String name) {
    return ParametroGeralMapper.INSTANCE.toResponseDTO(service.findByName(name));
  }
}
