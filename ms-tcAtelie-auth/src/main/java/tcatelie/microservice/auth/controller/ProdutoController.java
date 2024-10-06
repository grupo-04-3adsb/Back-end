package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.service.ProdutoService;
@RestController
@RequiredArgsConstructor
@RequestMapping("produtos")
@Tag(name = "Produtos", description = "Operações relacionadas a produtos.")
public class ProdutoController {

    private final ProdutoService service;

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
        try {
            ProdutoResponseDTO produtoResponse = service.cadastrarProduto(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
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
        try {
            ProdutoResponseDTO produtoResponse = service.buscarProdutoPorId(idProduto);
            return ResponseEntity.ok(produtoResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
