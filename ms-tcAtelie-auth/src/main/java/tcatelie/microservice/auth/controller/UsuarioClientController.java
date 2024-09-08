package tcatelie.microservice.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.UsuarioClientRequestDTO;
import tcatelie.microservice.auth.service.UsuarioClientService;


@RestController
@RequestMapping("/clientes")
public class UsuarioClientController {

    @Autowired
    private UsuarioClientService service;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam  String senha) {
        try {
            return service.login(email, senha);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas ou ocorreu um erro durante o login.");
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioClientRequestDTO usuario) {
        try {
            return service.cadastrarUsuario(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro durante o cadastro do usuário.");
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarUsuario(@RequestBody UsuarioClientRequestDTO usuario){
        try {
            service.atualizarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro durante a atulização do usuário.");
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable int id){
        try{
            service.deletarUsuario(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuário deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ocorreu um erro durante o delete do usuário.");
        }
    }
}
