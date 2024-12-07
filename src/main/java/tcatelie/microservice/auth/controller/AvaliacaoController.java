package tcatelie.microservice.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.AvaliacaoRequestDTO;
import tcatelie.microservice.auth.dto.response.AvaliacaoResponseDTO;
import tcatelie.microservice.auth.service.AvaliacaoService;

import java.util.LinkedList;
import java.util.Queue;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    // Fila para armazenar as avaliações pendentes
    private final Queue<AvaliacaoRequestDTO> filaAvaliacao = new LinkedList<>();

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
    public ResponseEntity obterAvaliacoesPorProduto(@PathVariable Integer produtoId) {
        return ResponseEntity.ok().body(avaliacaoService.obterAvaliacoesPorProduto(produtoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity obterAvaliacoesPorUsuario(@PathVariable Integer usuarioId) {
        return avaliacaoService.obterAvaliacoesPorUsuario(usuarioId);
    }

    @GetMapping("/produto/{produtoId}/media")
    public ResponseEntity calcularMediaAvaliacaoProduto(@PathVariable Integer produtoId) {
        return avaliacaoService.calcularMediaAvaliacaoProduto(produtoId);
    }

    // Métodos relacionados à fila

    @PostMapping("/fila")
    public ResponseEntity<String> adicionarAvaliacaoNaFila(@RequestBody AvaliacaoRequestDTO avaliacaoRequestDTO) {
        filaAvaliacao.add(avaliacaoRequestDTO);
        return ResponseEntity.ok("Avaliação adicionada à fila com sucesso!");
    }

    @PostMapping("/fila/andar")
    public ResponseEntity<String> processarProximaAvaliacao() {
        if (filaAvaliacao.isEmpty()) {
            return ResponseEntity.ok("A fila está vazia, nenhuma avaliação para processar.");
        }
        AvaliacaoRequestDTO proximaAvaliacao = filaAvaliacao.poll();
        avaliacaoService.criarAvaliacao(proximaAvaliacao);
        return ResponseEntity.ok("A próxima avaliação foi processada com sucesso!");
    }

    @GetMapping("/fila/info")
    public ResponseEntity<Object> obterInformacoesFila() {
        if (filaAvaliacao.isEmpty()) {
            return ResponseEntity.ok("A fila está vazia.");
        }

        AvaliacaoRequestDTO primeiro = filaAvaliacao.peek();
        AvaliacaoRequestDTO ultimo = null;
        for (AvaliacaoRequestDTO avaliacao : filaAvaliacao) {
            ultimo = avaliacao; // O último será atualizado a cada iteração
        }

        return ResponseEntity.ok(new FilaInfoResponse(
                filaAvaliacao.size(),
                primeiro,
                ultimo
        ));
    }

    // Classe auxiliar para informações da fila
    private static class FilaInfoResponse {
        private int tamanho;
        private AvaliacaoRequestDTO primeiro;
        private AvaliacaoRequestDTO ultimo;

        public FilaInfoResponse(int tamanho, AvaliacaoRequestDTO primeiro, AvaliacaoRequestDTO ultimo) {
            this.tamanho = tamanho;
            this.primeiro = primeiro;
            this.ultimo = ultimo;
        }

        public int getTamanho() {
            return tamanho;
        }

        public AvaliacaoRequestDTO getPrimeiro() {
            return primeiro;
        }

        public AvaliacaoRequestDTO getUltimo() {
            return ultimo;
        }
    }
}
