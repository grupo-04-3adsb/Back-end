package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.filter.CustoOutrosFiltroDTO;
import tcatelie.microservice.auth.dto.request.CustoOutrosRequestDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.service.CustoOutrosService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/custos-outros")
@RequiredArgsConstructor
public class CustoOutrosController {

    private static final Logger logger = LoggerFactory.getLogger(CustoOutrosController.class);

    private final CustoOutrosService service;

    @Operation(
            summary = "Buscar todos os custos de outros paginado",
            description = "Buscar todos os custos de outros de acordo com os filtros informados",
            tags = {"custos-outros"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Custos de outros encontrados"
                    )
            }
    )
    @GetMapping
    public Page<CustoOutrosResponseDTO> findAllPaginado(
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) Double valorMin,
            @RequestParam(required = false) Double valorMax,
            @RequestParam(required = false) String dataHoraAtualizacao,
            @RequestParam(required = false) String dataHoraCriacao,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataHoraAtualizacao") String sortBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(required = false) String dataHoraCriacaoInicio,
            @RequestParam(required = false) String dataHoraCriacaoFim,
            @RequestParam(required = false) String dataHoraAtualizacaoInicio,
            @RequestParam(required = false) String dataHoraAtualizacaoFim
    ) {
        logger.info("Buscando todos os custos de outros paginado");

        CustoOutrosFiltroDTO filtro = CustoOutrosFiltroDTO.builder()
                .descricao(descricao)
                .valorMin(valorMin)
                .valorMax(valorMax)
                .dataHoraAtualizacao(dataHoraAtualizacao)
                .dataHoraAtualizacaoFim(dataHoraAtualizacaoFim != null ? LocalDateTime.parse(dataHoraAtualizacaoFim) : null)
                .dataHoraAtualizacaoInicio(dataHoraAtualizacaoInicio != null ? LocalDateTime.parse(dataHoraAtualizacaoInicio) : null)
                .dataHoraCriacaoFim(dataHoraCriacaoFim != null ? LocalDateTime.parse(dataHoraCriacaoFim) : null)
                .dataHoraCriacaoInicio(dataHoraCriacaoInicio != null ? LocalDateTime.parse(dataHoraCriacaoInicio) : null)
                .dataHoraCriacao(dataHoraCriacao)
                .build();
        return service.findAllPaginado(
                filtro, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), sortBy))
        );
    }

    @Operation(
            summary = "Buscar todos os custos de outros",
            description = "Buscar todos os custos de outros",
            tags = {"custos-outros"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Custos de outros encontrados"
                    )
            }
    )
    @GetMapping("/all")
    public List<CustoOutrosResponseDTO> findAll() {
        logger.info("Buscando todos os custos de outros");

        return service.findAll();
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar custo de outros",
            description = "Cadastrar custo de outros",
            tags = {"custos-outros"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Custo de outros cadastrado"
                    )
            }
    )
    public void cadastrarCustoOutro(@RequestBody CustoOutrosRequestDTO requestDTO){
        logger.info("Cadastrando custo de outros");
        service.cadastrarCustoOutro(requestDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Editar custo de outros",
            description = "Editar custo de outros",
            tags = {"custos-outros"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Custo de outros editado"
                    )
            }
    )
    public void editarCustoOutro(@PathVariable Integer id, @RequestBody CustoOutrosRequestDTO requestDTO){
        logger.info("Editando custo de outros");
        service.editarCustoOutro(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover custo de outros",
            description = "Remover custo de outros",
            tags = {"custos-outros"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Custo de outros removido"
                    )
            }
    )
    public void removerCustoOutro(@PathVariable Integer id){
        logger.info("Removendo custo de outros");
        service.removerCustoOutro(id);
    }

}
