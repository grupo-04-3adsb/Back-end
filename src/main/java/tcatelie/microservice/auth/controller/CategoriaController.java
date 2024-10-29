package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tcatelie.microservice.auth.dto.request.CategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.mapper.CategoriaMapper;
import tcatelie.microservice.auth.service.CategoriaService;
import tcatelie.microservice.auth.model.Categoria;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
@Tag(name = "Categoria", description = "Operações relacionadas as categorias.")
public class CategoriaController {

    private final CategoriaService service;
    private final CategoriaMapper mapper;

    @Operation(
            summary = "Cadastro de categorias",
            description = "Este endpoint permite cadastrar uma nova categoria.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos."),
                    @ApiResponse(responseCode = "409", description = "Conflito: já existe uma categoria com esse nome."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @PostMapping
    public ResponseEntity cadastrarCategoria(@RequestBody @Valid CategoriaRequestDTO requestDTO){
        CategoriaResponseDTO categoriaResponse = service.cadastrarCategoria(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaResponse);
    }

    @Operation(
            summary = "Listagem de categorias",
            description = "Este endpoint permite listar todas as categorias cadastradas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoriaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @GetMapping
    public ResponseEntity listarCategoria(){
        List<CategoriaResponseDTO> categoriasBuscadas = service.listarCategoria();
        return categoriasBuscadas.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(categoriasBuscadas);
    }

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Este endpoint permite buscar uma categoria específica pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoriaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "ID inválido fornecido."),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Integer id){
        Categoria categoriaBuscada = service.findById(id);
        return ResponseEntity.ok(mapper.toCategoriaResponse(categoriaBuscada));
    }

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

    @Operation(
            summary = "Atualizar categoria",
            description = "Este endpoint permite atualizar uma categoria existente pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoriaResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou ID incorreto fornecido."),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
                    @ApiResponse(responseCode = "409", description = "Conflito: já existe uma categoria com este nome."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Integer id,
                                    @RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO){
        CategoriaResponseDTO categoriaSalva = service.atualizar(categoriaRequestDTO, id);
        return ResponseEntity.ok(categoriaSalva);
    }

    @Operation(
            summary = "Deletar categoria",
            description = "Este endpoint permite deletar uma categoria existente pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID inválido fornecido."),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
