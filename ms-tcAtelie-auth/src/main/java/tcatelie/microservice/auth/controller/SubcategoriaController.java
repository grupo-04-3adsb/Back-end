package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.SubcategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.model.Subcategoria;
import tcatelie.microservice.auth.service.SubcategoriaService;

import java.util.List;

@RestController
@RequestMapping("/subcategorias")
@RequiredArgsConstructor
@Tag(name = "Subcategoria", description = "Endpoints para interagir com a subcategoria.")
public class SubcategoriaController {

    private final SubcategoriaService service;

    @Operation(summary = "Cadastrar subcategoria", description = "Este endpoint permite cadastrar uma nova subcategoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subcategoria cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PostMapping
    public ResponseEntity cadastrarSubCategoria(@RequestBody @Valid SubcategoriaRequestDTO DTO){
        SubcategoriaResponseDTO subCategoriaResponse = service.cadastrarSubcategoria(DTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategoriaResponse);
    }

    @Operation(summary = "Listar subcategorias", description = "Este endpoint permite listar todas as subcategorias cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subcategorias listadas com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhuma subcategoria encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping
    public ResponseEntity listarSubCategoria(){
        List<SubcategoriaResponseDTO> subCategoriaBuscadas = service.ListarSubscategorias();
        return subCategoriaBuscadas.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(subCategoriaBuscadas);
    }

    @Operation(summary = "Buscar subcategoria por ID", description = "Este endpoint permite buscar uma subcategoria específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subcategoria encontrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido fornecido."),
            @ApiResponse(responseCode = "404", description = "Subcategoria não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Integer id){
        Subcategoria subcategoriaBuscada = service.findById(id);
        return ResponseEntity.ok(subcategoriaBuscada);
    }

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

    @Operation(summary = "Atualizar subcategoria", description = "Este endpoint permite atualizar uma subcategoria existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subcategoria atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ID incorreto fornecido."),
            @ApiResponse(responseCode = "404", description = "Subcategoria não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Integer id,
                                    @RequestBody @Valid SubcategoriaRequestDTO requestDTO){
        SubcategoriaResponseDTO subCategoriaSalva = service.atualizar(requestDTO, id);
        return ResponseEntity.ok(subCategoriaSalva);
    }

    @Operation(summary = "Deletar subcategoria", description = "Este endpoint permite deletar uma subcategoria existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subcategoria deletada com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido fornecido."),
            @ApiResponse(responseCode = "404", description = "Subcategoria não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}