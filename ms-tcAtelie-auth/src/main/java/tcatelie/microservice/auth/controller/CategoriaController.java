package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.service.CategoriaService;
import tcatelie.microservice.auth.model.Categoria;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
@Tag(name = "Categoria", description = "Operações relacionadas as categorias.")
public class CategoriaController {

    private final CategoriaService service;

    @Operation(summary = "Pesquisa categorias pelo nome", description = "Este endpoint permite pesquisar categorias pelo nome fornecido. A busca é realizada de forma paginada, retornando 10 resultados por vez.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Categorias não encontradas")
    })
    @GetMapping("/pesquisar")
    public Page<CategoriaResponseDTO> pesquisarCategorias(
            @Parameter(description = "Nome da categoria a ser pesquisada", required = false)
            @RequestParam String nome,
            @Parameter(description = "Número da página para paginar os resultados", required = false, example = "0")
            @RequestParam(defaultValue = "0") int pagina) {
        return service.pesquisarPorNome(nome, PageRequest.of(pagina, 10));
    }
}
