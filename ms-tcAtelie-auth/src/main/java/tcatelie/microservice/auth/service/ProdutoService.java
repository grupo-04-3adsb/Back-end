package tcatelie.microservice.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.mapper.ProdutoMapper;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoMapper mapper;

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoMapper mapper, ProdutoRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarProduto(ProdutoRequestDTO produtoRequest){
        try{
            Produto produto = mapper.toEntity(produtoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante o cadastro do produto");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarProduto(){
        try{
            if(repository.findAll().isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .build();
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(repository.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro na listagem de produtos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProduto(@PathVariable int id){
        try{
            Optional<Produto> produtoId = repository.findById(id);
            return produtoId.map(produto -> ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(produto)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public
}
