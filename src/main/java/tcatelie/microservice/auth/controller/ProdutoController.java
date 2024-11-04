package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.filter.ProdutoFiltroDTO;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.request.ProdutosUpdateRequestDTO;
import tcatelie.microservice.auth.dto.response.MercadoLivreProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.service.ProdutoService;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("produtos")
@Tag(name = "Produtos", description = "Operações relacionadas a produtos.")
public class ProdutoController {

    private final ProdutoService service;

    private final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Operation(
            summary = "Cadastro de produtos",
            description = "Este endpoint permite cadastrar um novo produto.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos."),
                    @ApiResponse(responseCode = "409", description = "Conflito: já existe um produto com esse nome."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @PostMapping
    public ResponseEntity cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO requestDTO) {
        ProdutoResponseDTO produtoResponse = service.cadastrarProduto(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);

    }

    @Operation(summary = "Buscar produto por ID", description = "Este endpoint retorna um produto com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping("/{idProduto}")
    public ResponseEntity buscarProdutoPorId(@PathVariable Integer idProduto) {
        ProdutoResponseDTO produtoResponse = service.buscarProdutoPorId(idProduto);
        return ResponseEntity.ok(produtoResponse);

    }

    @Operation(
            summary = "Listar todos os produtos com paginação",
            description = "Este endpoint retorna uma lista paginada de produtos cadastrados. " +
                    "É possível especificar o número da página, o tamanho da página e a ordenação dos resultados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de produtos retornada com sucesso.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping
    public ResponseEntity buscarTodosProdutosComPaginacao(

            @Parameter(description = "Nome do produto", required = false, example = "Caderno")
            @RequestParam(value = "nome", required = false) String nome,

            @Parameter(description = "SKU do produto", required = false, example = "123456")
            @RequestParam(value = "sku", required = false) String sku,

            @Parameter(description = "Margem de lucro mínima do produto", required = false, example = "0.1")
            @RequestParam(value = "margemLucroMinima", required = false) Double margemLucroMinima,

            @Parameter(description = "Margem de lucro máxima do produto", required = false, example = "0.5")
            @RequestParam(value = "margemLucroMaxima", required = false) Double margemLucroMaxima,

            @Parameter(description = "Preço mínimo do produto", required = false, example = "10.0")
            @RequestParam(value = "precoMinimo", required = false) Double precoMinimo,

            @Parameter(description = "Preço máximo do produto", required = false, example = "100.0")
            @RequestParam(value = "precoMaximo", required = false) Double precoMaximo,

            @Parameter(description = "Nome da categoria", required = false, example = "Papelaria")
            @RequestParam(value = "nomeCategoria", required = false) String nomeCategoria,

            @Parameter(description = "Nome da subcategoria", required = false, example = "Caderno")
            @RequestParam(value = "nomeSubcategoria", required = false) String nomeSubcategoria,

            @Parameter(description = "Indica se o produto é personalizável", required = false, example = "true")
            @RequestParam(value = "isPersonalizavel", required = false) Boolean isPersonalizavel,

            @Parameter(description = "Indica se a personalização é obrigatória", required = false, example = "true")
            @RequestParam(value = "isPersonalizacaoObrigatoria", required = false) Boolean isPersonalizacaoObrigatoria,

            @Parameter(description = "Número da página para paginar os resultados", required = false, example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Tamanho da página para paginar os resultados", required = false, example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Ordenação dos resultados", required = false, example = "ASC")
            @RequestParam(defaultValue = "asc") String sortOrder,

            @Parameter(description = "Campo para ordenação dos resultados", required = false, example = "nome")
            @RequestParam(defaultValue = "id") String sortBy) {

        ProdutoFiltroDTO filtroProduto = new ProdutoFiltroDTO(
                nome,
                sku,
                margemLucroMinima,
                margemLucroMaxima,
                precoMinimo,
                precoMaximo,
                nomeCategoria,
                nomeSubcategoria,
                isPersonalizavel,
                isPersonalizacaoObrigatoria
        );

        Page<ProdutoResponseDTO> produtosPaginados = service.buscarTodosProdutosPaginados(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy)), filtroProduto);
        return ResponseEntity.ok(produtosPaginados);
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Este endpoint permite atualizar um produto existente baseado no seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos."),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @PutMapping("/{idProduto}")
    public ResponseEntity atualizarProduto(
            @PathVariable Integer idProduto,
            @RequestBody @Valid ProdutoRequestDTO requestDTO) throws IOException {
        ProdutoResponseDTO produtoAtualizado = service.atualizarProduto(idProduto, requestDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(
            summary = "Desativar produto",
            description = "Este endpoint permite desativar um produto existente baseado no seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto desativado com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @PutMapping("desativar/{idProduto}")
    public ResponseEntity desativarProduto(@PathVariable Integer idProduto) {
        ProdutoResponseDTO produtoDesativado = service.desativarProduto(idProduto);
        return ResponseEntity.ok(produtoDesativado);
    }

    @Operation(
            summary = "Buscar produtos do Mercado Livre",
            description = "Este endpoint permite buscar produtos do Mercado Livre ordenados pelo preço de forma decrescente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos do Mercado Livre encontrados com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MercadoLivreProdutoResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @GetMapping("/mercado-livre")
    public ResponseEntity buscarProdutosMercadoLivre() throws Exception {
        return ResponseEntity.ok(service.ordenarProdutosMercadoLivrePrecoDecrescente());
    }

    @Operation(
            summary = "Exportar produtos para CSV",
            description = "Este endpoint permite exportar a lista de produtos para um arquivo CSV.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos exportados com sucesso.",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @GetMapping("/exportar-csv")
    public ResponseEntity<InputStreamResource> exportarProdutosCsv() {
        try {
            String filePath = "listaProdutos.csv";

            service.exportarCSVListaProdutos(filePath);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=listaProdutos.csv");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        } catch (IOException e) {
            logger.error("Erro ao exportar produtos para CSV: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Pesquisa binária",
            description = "Este endpoint permite buscar um produto por nome utilizando pesquisa binária.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProdutoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @GetMapping("/pesquisa-binaria")
    public ResponseEntity buscarProdutoPorNome(@RequestParam String nome) {
        ProdutoResponseDTO produtoResponse = service.buscarProdutoPorNomePesquisaBinaria(nome);

        if (produtoResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoResponse);
    }

    @Operation(
            summary = "Atualizar categoria e subcategoria de produtos",
            description = "Este endpoint permite atualizar a categoria e subcategoria de um ou mais produtos.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria e subcategoria atualizadas com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            }
    )
    @PutMapping
    public ResponseEntity atualizarProdutos(@RequestBody @Valid ProdutosUpdateRequestDTO requestDTO) {
        service.atualizarCategoriaSubcategoriaDoProduto(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar-por-material/{idMaterial}")
    public ResponseEntity listarProdutosPorMaterial(@PathVariable Integer idMaterial,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "asc") String sortOrder,
                                                    @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<ProdutoResponseDTO> produtos = service.buscarProdutosPorIdMaterial(idMaterial, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy)));
        return ResponseEntity.ok(produtos);
    }
}
