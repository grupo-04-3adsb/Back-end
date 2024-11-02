package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.filter.SubcategoriaFiltroDTO;
import tcatelie.microservice.auth.dto.request.SubcategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.model.Subcategoria;
import tcatelie.microservice.auth.service.SubcategoriaService;

import java.time.LocalDateTime;
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
    public ResponseEntity cadastrarSubCategoria(@RequestBody @Valid SubcategoriaRequestDTO DTO) {
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
    public ResponseEntity listarSubCategoria() {
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
    public ResponseEntity buscarPorId(@PathVariable Integer id) {
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
    ) {
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
                                    @RequestBody @Valid SubcategoriaRequestDTO requestDTO) {
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
    public ResponseEntity deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar subcategorias", description = "Este endpoint permite filtrar subcategorias de acordo com os parâmetros fornecidos."
            , responses = {
            @ApiResponse(responseCode = "200", description = "Subcategorias filtradas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "404", description = "Subcategorias não encontradas.")
    })
    @GetMapping("/filtrar")
    public Page<SubcategoriaResponseDTO> filtrar(
            @Parameter(description = "Nome da subcategoria buscada", example = "Cadernos")
            @RequestParam(defaultValue = "") String nomeSubcategoria,
            @Parameter(description = "Descrição da subcategoria buscada", example = "Cadernos de capa dura")
            @RequestParam(defaultValue = "") String descricaoSubcategoria,
            @Parameter(description = "Quantidade mínima de produtos", example = "10")
            @RequestParam(defaultValue = "") Integer quantidadeMinimaProdutos,
            @Parameter(description = "Quantidade máxima de produtos", example = "100")
            @RequestParam(defaultValue = "") Integer quantidadeMaximaProdutos,
            @Parameter(description = "Nome da categoria buscada", example = "Cadernos")
            @RequestParam(defaultValue = "") String nomeCategoria,
            @Parameter(description = "Data de cadastro início", example = "2021-01-01T00:00:00")
            @RequestParam(defaultValue = "") String dataCadastroInicio,
            @Parameter(description = "Data de cadastro fim", example = "2021-01-01T00:00:00")
            @RequestParam(defaultValue = "") String dataCadastroFim,
            @Parameter(description = "Data de atualização inicial", example = "2021-01-01T00:00:00")
            @RequestParam(defaultValue = "") String dataAtualizacaoInicio,
            @Parameter(description = "Data de atualização final", example = "2021-01-01T00:00:00")
            @RequestParam(defaultValue = "") String dataAtualizacaoFim,
            @Parameter(description = "Número da página para paginar os resultados", required = false, example = "0")
            @RequestParam(defaultValue = "0") int pagina,

            @Parameter(description = "Tamanho da página para paginar os resultados", required = false, example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Ordenação dos resultados", required = false, example = "ASC")
            @RequestParam(defaultValue = "asc") String sortOrder,

            @Parameter(description = "Campo para ordenação dos resultados", required = false, example = "nomeCategoria")
            @RequestParam(defaultValue = "idSubcategoria") String sortBy
    ) {

        SubcategoriaFiltroDTO filtro = new SubcategoriaFiltroDTO();

        filtro.setNomeSubcategoria(nomeSubcategoria);
        filtro.setDescricaoSubcategoria(descricaoSubcategoria);
        filtro.setQuantidadeMinimaProdutos(quantidadeMinimaProdutos);
        filtro.setQuantidadeMaximaProdutos(quantidadeMaximaProdutos);
        filtro.setNomeCategoria(nomeCategoria);

        if (StringUtils.isNotBlank(dataCadastroInicio)) {
            filtro.setDataCadastroInicio(LocalDateTime.parse(dataCadastroInicio));
        }
        if (StringUtils.isNotBlank(dataCadastroFim)) {
            filtro.setDataCadastroFim(LocalDateTime.parse(dataCadastroFim));
        }
        if (StringUtils.isNotBlank(dataAtualizacaoInicio)) {
            filtro.setDataAtualizacaoInicio(LocalDateTime.parse(dataAtualizacaoInicio));
        }
        if (StringUtils.isNotBlank(dataAtualizacaoFim)) {
            filtro.setDataAtualizacaoFim(LocalDateTime.parse(dataAtualizacaoFim));
        }

        return service.filtrar(filtro, PageRequest.of(pagina, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy)));
    }
}
