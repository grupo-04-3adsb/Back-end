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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.filter.MaterialFiltroDTO;
import tcatelie.microservice.auth.dto.request.MaterialRequestDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialDetalhadoResponseDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialResponseDTO;
import tcatelie.microservice.auth.service.MaterialService;

import java.time.LocalDateTime;
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
    ) {
        MaterialResponseDTO materialCadastrado = materialService.cadastrar(requestDTO);
        return ResponseEntity.status(201).body(materialCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> buscar() {
        List<MaterialResponseDTO> materiaisBuscados = materialService.buscar();

        return materiaisBuscados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(materiaisBuscados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDetalhadoResponseDTO> buscarPorId(@PathVariable Integer id) {

        MaterialDetalhadoResponseDTO materialBuscado = materialService.buscarPorId(id);
        return ResponseEntity.ok(materialBuscado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDetalhadoResponseDTO> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid MaterialRequestDTO materialRequestDTO
    ) {
        MaterialDetalhadoResponseDTO materialSalvo = materialService.atualizar(materialRequestDTO, id);
        return ResponseEntity.ok(materialSalvo);
    }

    @Operation(summary = "Revisão de material", description = "Este endpoint permite realizar a revisão de um material, alterando o preço unitário do mesmo.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Revisão realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
            })
    @GetMapping("/revisao")
    public ResponseEntity getRevisoes(

            @Parameter(description = "Identificador do material", required = true, example = "1")
            @RequestParam(required = true) Integer idMaterial,

            @Parameter(description = "Preço unitário novo do material", required = true, example = "1.0")
            @RequestParam(required = true) Double precoUnitarioNovo,

            @Parameter(description = "Número da página para paginar os resultados", required = false, example = "0")
            @RequestParam(defaultValue = "0") Integer page,

            @Parameter(description = "Tamanho da página para paginar os resultados", required = false, example = "10")
            @RequestParam(defaultValue = "10") Integer size

    ) {
        return ResponseEntity.ok(materialService.getRevisoes(PageRequest.of(page, size), idMaterial, precoUnitarioNovo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {

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

    @Operation(summary = "Filtrar materiais", description = "Este endpoint permite filtrar materiais com base em diversos parâmetros.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Materiais filtrados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
            })
    @GetMapping("/filtrar")
    public Page<MaterialDetalhadoResponseDTO> filtrarMateriais(
            @Parameter(description = "Nome do material a ser pesquisada", required = false)
            @RequestParam(required = false) String nomeMaterial,

            @Parameter(description = "Número da página para paginar os resultados", required = false, example = "0")
            @RequestParam(defaultValue = "0") int pagina,

            @Parameter(description = "Tamanho da página para paginar os resultados", required = false, example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Ordenação dos resultados", required = false, example = "ASC")
            @RequestParam(defaultValue = "asc") String sortOrder,

            @Parameter(description = "Preço unitário minímo", required = false, example = "1")
            @RequestParam(required = false) Double precoUnitarioMinimo,

            @Parameter(description = "Preço unitário máximo", required = false, example = "1")
            @RequestParam(required = false) Double precoUnitarioMaximo,

            @Parameter(description = "Data criação inicial", required = false, example = "2021-01-01T00:00:00")
            @RequestParam(required = false) String dataCriacaoInicio,

            @Parameter(description = "Data criação final", required = false, example = "2021-01-01T00:00:00")
            @RequestParam(required = false) String dataCriacaoFim,

            @Parameter(description = "Data atualização inicial", required = false, example = "2021-01-01T00:00:00")
            @RequestParam(required = false) String dataAtualizacaoInicio,

            @Parameter(description = "Data atualização final", required = false, example = "2021-01-01T00:00:00")
            @RequestParam(required = false) String dataAtualizacaoFim,

            @Parameter(description = "Campo para ordenação dos resultados", required = false, example = "nomeMaterial")
            @RequestParam(defaultValue = "idMaterial") String sortBy) {

        MaterialFiltroDTO filtro = new MaterialFiltroDTO();

        if (StringUtils.isNotBlank(dataCriacaoFim)) {
            filtro.setDataHoraCadastroFim(LocalDateTime.parse(dataCriacaoFim));
        }
        if (StringUtils.isNotBlank(dataCriacaoInicio)) {
            filtro.setDataHoraCadastroInicio(LocalDateTime.parse(dataCriacaoInicio));
        }
        if (StringUtils.isNotBlank(dataAtualizacaoInicio)) {
            filtro.setDataHoraAtualizacaoInicio(LocalDateTime.parse(dataAtualizacaoInicio));
        }
        if (StringUtils.isNotBlank(dataAtualizacaoFim)) {
            filtro.setDataHoraAtualizacaoFim(LocalDateTime.parse(dataAtualizacaoFim));
        }

        filtro.setNomeMaterial(nomeMaterial);
        filtro.setPrecoUnitarioMinimo(precoUnitarioMinimo);
        filtro.setPrecoUnitarioMaximo(precoUnitarioMaximo);

        return materialService.filtrar(PageRequest.of(pagina, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy)), filtro);
    }
}
