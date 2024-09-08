package tcatelie.microservice.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
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

    public ResponseEntity<?> cadastrarProduto(ProdutoRequestDTO produtoRequest){
        try{
            Produto produto = mapper.toEntity(produtoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante o cadastro do produto");
        }
    }

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

    public ResponseEntity<Produto> buscarProduto(ProdutoResponseDTO produtoResponse){
        try{
            Optional<Produto> produtoById = repository.findById(produtoResponse.getId());

            return produtoById.map(produto -> ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(produto)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<String> atualizarProduto(ProdutoRequestDTO produtoRequest, @RequestParam int id){
        try{
            Optional<Produto> produtoId = repository.findById(id);

            if(produtoId.isPresent()){
                Produto existingProduct = produtoId.get();

                existingProduct.setNome(produtoRequest.getNome());
                existingProduct.setPreco(produtoRequest.getPreco());
                existingProduct.setDescricao(produtoRequest.getDescricao());
                existingProduct.setMaterial(produtoRequest.getMaterial());
                existingProduct.setDimensao(produtoRequest.getDimensao());
                existingProduct.setUrl_produto(produtoRequest.getUrl_produto());
                repository.save(existingProduct);

                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body("Atualização de dados feito com sucesso.");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Não foi possivel identificar o produto.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante a atualização do produto.");
        }
    }

    public ResponseEntity<String> deletarProduto(@RequestParam int id){
        try{
            Optional<Produto> produtoId = repository.findById(id);

            if(produtoId.isPresent()){
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body("Produto deletado com sucesso.");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Não foi possivel identificar o produto.");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro durante a deleção do produto.");
        }
    }
}
