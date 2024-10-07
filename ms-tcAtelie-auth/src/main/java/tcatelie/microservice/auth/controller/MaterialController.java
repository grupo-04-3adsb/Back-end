package tcatelie.microservice.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.MaterialRequestDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialDetalhadoResponseDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialResponseDTO;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.service.MaterialService;

import java.util.List;

@RestController
@RequestMapping("/materiais")
@RequiredArgsConstructor
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

}
