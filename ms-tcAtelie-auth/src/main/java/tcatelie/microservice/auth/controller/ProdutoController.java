package tcatelie.microservice.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarProduto(@RequestBody ProdutoRequestDTO produto){
        try{
            return service.cadastrarProduto(produto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro durante o cadastro do produto");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarProdutos(){
        try{
            return service.listarProduto();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro na listagem de produtos");
        }
    }
}
