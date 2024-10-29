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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.MaterialRequestDTO;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialDetalhadoResponseDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialResponseDTO;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.service.MaterialService;

import java.util.List;

@RestController
@RequestMapping("/materiais")
@RequiredArgsConstructor
@Tag(name = "Material", description = "Operações relacionadas aos materiais.")
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public ResponseEntity<MaterialResponseDTO> cadastrar(
            @RequestBody @Valid MaterialRequestDTO requestDTO
            ){
        MaterialResponseDTO materialCadastrado = materialService.cadastrar(requestDTO);
        return ResponseEntity.created(null).body(materialCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> buscar(){
        List<MaterialResponseDTO> materiaisBuscados = materialService.buscar();

        return materiaisBuscados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(materiaisBuscados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDetalhadoResponseDTO> buscarPorId(@PathVariable Integer id){

        MaterialDetalhadoResponseDTO materialBuscado = materialService.buscarPorId(id);
        return ResponseEntity.ok(materialBuscado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDetalhadoResponseDTO> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid MaterialRequestDTO materialRequestDTO
    ){
        MaterialDetalhadoResponseDTO materialSalvo = materialService.atualizar(materialRequestDTO, id);
        return ResponseEntity.ok(materialSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){

        materialService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Pesquisa material pelo nome", description = "Este endpoint permite pesquisar materiais pelo nome fornecido. A busca é realizada de forma paginada, retornando 10 resultados por vez.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materiais encontradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Materiais não encontradas")
    })
    @GetMapping("/pesquisar")
    public Page<MaterialDetalhadoResponseDTO> pesquisarMaterial(
            @Parameter(description = "Nome do material a ser pesquisada", required = false)
            @RequestParam String nome,
            @Parameter(description = "Número da página para paginar os resultados", required = false, example = "0")
            @RequestParam(defaultValue = "0") int pagina) {
        return materialService.pesquisarPorNome(nome, PageRequest.of(pagina, 10));
    }
}
