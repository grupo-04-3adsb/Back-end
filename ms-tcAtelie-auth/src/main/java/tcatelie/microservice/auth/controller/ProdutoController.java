package tcatelie.microservice.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping()
    public ResponseEntity<?> cadastrarProduto(@RequestBody ProdutoRequestDTO produto){
        try{
            return service.cadastrarProduto(produto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro durante o cadastro do produto");
        }
    }

    @GetMapping()
    public ResponseEntity<?> listarProdutos(){
        try{
            return service.listarProduto();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro na listagem de produtos");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProduto(@RequestBody ProdutoRequestDTO produto, @PathVariable Integer id){
        try{
            service.atualizarProduto(produto, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Produto atualizado com sucesso.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro durante a atulização do produto.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProduto(@PathVariable int id){
        try{
            return service.buscarProduto(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro durante a busca do produto.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable int id){
        try{
            service.deletarProduto(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Produto deletado com sucesso.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro durante a deleção do produto.");
        }
    }
}
