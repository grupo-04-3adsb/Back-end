package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.filter.ProdutoFiltroDTO;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
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
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "sku", required = false) String sku,
            @RequestParam(value = "margemLucroMinima", required = false) Double margemLucroMinima,
            @RequestParam(value = "margemLucroMaxima", required = false) Double margemLucroMaxima,
            @RequestParam(value = "precoMinimo", required = false) Double precoMinimo,
            @RequestParam(value = "precoMaximo", required = false) Double precoMaximo,
            @RequestParam(value = "nomeCategoria", required = false) String nomeCategoria,
            @RequestParam(value = "nomeSubcategoria", required = false) String nomeSubcategoria,
            @RequestParam(value = "isPersonalizavel", required = false) Boolean isPersonalizavel,
            @RequestParam(value = "isPersonalizacaoObrigatoria", required = false) Boolean isPersonalizacaoObrigatoria) {

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

        Page<ProdutoResponseDTO> produtosPaginados = service.buscarTodosProdutosPaginados(pageable, filtroProduto);
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

    /*
        Endponts adicionais para apresentar no dia da Sprint 2
    */

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

        if(produtoResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoResponse);
    }
}