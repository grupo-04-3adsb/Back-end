package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcatelie.microservice.auth.dto.response.FAQResponseDTO;
import tcatelie.microservice.auth.service.FAQService;

import java.util.List;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FAQController {

  private final FAQService service;

  @Operation(summary = "Busca todas FAQs",
          description = "Busca todas as FAQs cadastradas no sistema",
          responses = {
                  @ApiResponse(responseCode = "200", description = "FAQs encontradas"),
                  @ApiResponse(responseCode = "500", description = "Erro interno")
          }
  )
  @GetMapping("/all")
  public List<FAQResponseDTO> findAll() {
    return service.findAll();
  }
}
