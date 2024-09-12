package tcatelie.microservice.auth.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.AuthenticationDTO;
import tcatelie.microservice.auth.dto.RegisterDTO;
import tcatelie.microservice.auth.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity buscaUsuarioPorEmailSenha(@RequestBody @Valid AuthenticationDTO dto){
        return service.buscarUsuarioEmailSenha(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity atualizarUsuario(
            @PathVariable Integer id,
            @RequestBody @Valid RegisterDTO dto,
            Authentication authentication) {

        return service.atualizarUsuario(id, dto, authentication);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity deletarUsuario(
            @PathVariable Integer id,
            Authentication authentication
    ){
        return service.deletarUsuario(id, authentication);
    }
}
