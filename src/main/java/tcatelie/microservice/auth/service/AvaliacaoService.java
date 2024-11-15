package tcatelie.microservice.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.request.AvaliacaoRequestDTO;
import tcatelie.microservice.auth.dto.response.AvaliacaoResponseDTO;
import tcatelie.microservice.auth.model.Avaliacao;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.repository.AvaliacaoRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.repository.UserRepository;
import tcatelie.microservice.auth.mapper.AvaliacaoMapper;

import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final UserRepository userRepository;
    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ProdutoRepository produtoRepository,
                            UserRepository userRepository, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.produtoRepository = produtoRepository;
        this.userRepository = userRepository;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    public ResponseEntity criarAvaliacao(AvaliacaoRequestDTO dto) {
        Optional<Produto> produto = produtoRepository.findById(dto.getProdutoId());
        Optional<Usuario> usuario = userRepository.findById(dto.getUsuarioId());

        if (produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto não encontrado.");
        }
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
        }

        Avaliacao avaliacao = avaliacaoMapper.toAvaliacao(dto);
        avaliacao.setProduto(produto.get());
        avaliacao.setUsuario(usuario.get());

        avaliacao = avaliacaoRepository.save(avaliacao);

        AvaliacaoResponseDTO responseDTO = avaliacaoMapper.toResponseDTO(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    public ResponseEntity atualizarAvaliacao(Integer id, AvaliacaoRequestDTO dto) {
        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository.findById(id);
        if (avaliacaoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrada.");
        }

        Avaliacao avaliacao = avaliacaoExistente.get();

        if (dto.getTitulo() != null && !dto.getTitulo().isEmpty()) {
            avaliacao.setTitulo(dto.getTitulo());
        }
        if (dto.getDescricao() != null && !dto.getDescricao().isEmpty()) {
            avaliacao.setDescricao(dto.getDescricao());
        }
        if (dto.getNota() != null) {
            avaliacao.setNotaAvaliacao(dto.getNota());
        }
        if (dto.getAprovada() != null) {
            avaliacao.setAvaliacaoAprovada(dto.getAprovada());
        }

        Optional<Produto> produto = produtoRepository.findById(dto.getProdutoId());
        Optional<Usuario> usuario = userRepository.findById(dto.getUsuarioId());

        if (produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto não encontrado.");
        }
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
        }

        avaliacao.setProduto(produto.get());
        avaliacao.setUsuario(usuario.get());

        avaliacao = avaliacaoRepository.save(avaliacao);

        AvaliacaoResponseDTO responseDTO = avaliacaoMapper.toResponseDTO(avaliacao);
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity excluirAvaliacao(Integer id) {
        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository.findById(id);
        if (avaliacaoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrada.");
        }

        avaliacaoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity obterAvaliacoesPorProduto(Integer produtoId) {
        if (!produtoRepository.existsById(produtoId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto não encontrado.");
        }

        var avaliacoes = avaliacaoRepository.findByProduto_Id(produtoId);
        var response = avaliacoes.stream().map(avaliacaoMapper::toResponseDTO).toList();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity obterAvaliacoesPorUsuario(Integer usuarioId) {
        if (!userRepository.existsById(usuarioId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
        }

        var avaliacoes = avaliacaoRepository.findByUsuario_IdUsuario(usuarioId);
        var response = avaliacoes.stream().map(avaliacaoMapper::toResponseDTO).toList();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity calcularMediaAvaliacaoProduto(Integer produtoId) {
        Optional<Produto> produto = produtoRepository.findById(produtoId);
        if (produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto não encontrado.");
        }

        Double mediaNota = avaliacaoRepository.calcularMediaNotaAprovada(produtoId);
        if (mediaNota == null) {
            return ResponseEntity.status(HttpStatus.OK).body("Produto ainda não tem avaliações.");
        }

        return ResponseEntity.ok(mediaNota);
    }
}
