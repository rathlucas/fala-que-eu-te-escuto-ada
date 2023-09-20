package dev.lucas.preview.controller;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.model.cadastro.Empresa;
import dev.lucas.preview.model.postagem.Elogio;
import dev.lucas.preview.model.postagem.Postagem;
import dev.lucas.preview.model.postagem.Reclamacao;
import dev.lucas.preview.repository.ClienteRepository;
import dev.lucas.preview.repository.EmpresaRepository;
import dev.lucas.preview.repository.PostagemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/postagens")
public class PostagemController {

    @Autowired
    PostagemRepository postagemRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<Postagem>> recuperarPostagens() {
        try {
            List<Postagem> postagens = new ArrayList<>(postagemRepository.findAll());
            if (postagens.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(postagens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Postagem> recuperarPostagemPorId(@PathVariable("id") int id) {
        try {
            Optional<Postagem> postagem = postagemRepository.findById(id);
            return postagem.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/criacao/elogio/{idCliente}/{idEmpresa}")
    @ResponseBody
    public ResponseEntity<Elogio> criarElogio(@Valid @RequestBody Elogio postagem,
                                              @PathVariable("idCliente") int idCliente,
                                              @PathVariable("idEmpresa") int idEmpresa) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(idCliente);
            if (cliente.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
            if (empresa.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Elogio _postagem = postagemRepository.save(new Elogio(
                    postagem.getTitulo(),
                    postagem.getMensagem(),
                    cliente.get(),
                    empresa.get()
                    )
            );
            return new ResponseEntity<>(_postagem, HttpStatus.CREATED);
        } catch(Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/criacao/reclamacao/{idCliente}/{idEmpresa}")
    @ResponseBody
    public ResponseEntity<Reclamacao> criarReclamacao(@Valid @RequestBody Reclamacao postagem,
                                                  @PathVariable("idCliente") int idCliente,
                                                  @PathVariable("idEmpresa") int idEmpresa) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(idCliente);
            if (cliente.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
            if (empresa.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Reclamacao _postagem = postagemRepository.save(new Reclamacao(
                            postagem.getTitulo(),
                            postagem.getMensagem(),
                            cliente.get(),
                            empresa.get()
                    )
            );
            return new ResponseEntity<>(_postagem, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Postagem> atualizarPorId(@PathVariable("id") int id, @RequestBody Postagem postagem) {
        try {
            Optional<Postagem> resultado = postagemRepository.findById(id);
            if (resultado.isPresent()) {
                Postagem _postagem = resultado.get();
                _postagem.setTitulo(postagem.getTitulo());
                _postagem.setMensagem(postagem.getMensagem());

                return new ResponseEntity<>(postagemRepository.save(_postagem), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> removerPostagem(@PathVariable("id") int id) {
        try {
            postagemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
