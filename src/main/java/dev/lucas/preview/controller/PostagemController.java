package dev.lucas.preview.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postagem")
public class PostagemController {

    @GetMapping
    public String olaMundo() {
        return "Ola, mundo!";
    }

    @PostMapping(value = "/cadastro")
    public String olaMundoPost(@RequestBody UsuarioDTO usuario) {
        return "Ola, post";
    }

    @PatchMapping(value = "/atualizar/{id}")
    public String olaMundoPatch(@PathVariable("id") int id) {
        return "Ola, patch";
    }

    @PutMapping
    public String olaMundoPut() {
        return "Ola, put";
    }

    @DeleteMapping
    public String olaMundoDelete() {
        return "Ola, delete";
    }

}
