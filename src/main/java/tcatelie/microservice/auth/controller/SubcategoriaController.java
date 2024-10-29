package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.service.SubcategoriaService;

@RestController
@RequestMapping("/subcategorias")
@RequiredArgsConstructor
@Tag(name = "Subcategoria", description = "Endpoints para interagir com a subcategoria.")
public class SubcategoriaController {

    private final SubcategoriaService service;

    @Operation(summary = "Pesquisa subcategoria pelo nome e categoria", description = "Este endpoint permite pesquisar subcategorias pelo nome fornecido e sua categoria. A busca é realizada de forma paginada, retornando 10 resultados por vez.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subcategoria encontradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Subcategoria não encontradas")
    })
    @GetMapping("/pesquisar/{idCategoria}")
    public Page<SubcategoriaResponseDTO> pesquisarSubcaategoria(
            @PathVariable Integer idCategoria,
            @Parameter(description = "Nome da subcategoria a ser pesquisada", required = false)
            @RequestParam(defaultValue = "") String nome,
            @Parameter(description = "Número da página", required = false)
            @RequestParam(defaultValue = "0") Integer pagina
    ){
        return service.pesquisarPorNome(nome, idCategoria, PageRequest.of(pagina, 10));
    }
}
