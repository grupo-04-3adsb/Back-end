package tcatelie.microservice.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.AvaliacaoRequestDTO;
import tcatelie.microservice.auth.dto.response.AvaliacaoResponseDTO;
import tcatelie.microservice.auth.service.AvaliacaoService;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@RequestBody AvaliacaoRequestDTO avaliacaoRequestDTO) {
        return (ResponseEntity<AvaliacaoResponseDTO>) avaliacaoService.criarAvaliacao(avaliacaoRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> atualizarAvaliacao(@PathVariable Integer id, @RequestBody AvaliacaoRequestDTO avaliacaoRequestDTO) {
        return (ResponseEntity<AvaliacaoResponseDTO>) avaliacaoService.atualizarAvaliacao(id, avaliacaoRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAvaliacao(@PathVariable Integer id) {
        return (ResponseEntity<Void>) avaliacaoService.excluirAvaliacao(id);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<?> obterAvaliacoesPorProduto(@PathVariable Integer produtoId) {
        return avaliacaoService.obterAvaliacoesPorProduto(produtoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obterAvaliacoesPorUsuario(@PathVariable Integer usuarioId) {
        return avaliacaoService.obterAvaliacoesPorUsuario(usuarioId);
    }

    @GetMapping("/produto/{produtoId}/media")
    public ResponseEntity<?> calcularMediaAvaliacaoProduto(@PathVariable Integer produtoId) {
        return avaliacaoService.calcularMediaAvaliacaoProduto(produtoId);
    }
}
