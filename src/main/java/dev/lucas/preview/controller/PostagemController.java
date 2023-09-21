package dev.lucas.preview.controller;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.model.cadastro.Empresa;
import dev.lucas.preview.model.postagem.Elogio;
import dev.lucas.preview.model.postagem.Postagem;
import dev.lucas.preview.model.postagem.Reclamacao;
import dev.lucas.preview.repository.ClienteRepository;
import dev.lucas.preview.repository.EmpresaRepository;
import dev.lucas.preview.repository.PostagemRepository;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Postagens", description = "Consulta e manipulação de postagens")
@RestController
@RequestMapping("/api/v1/postagens")
public class PostagemController {

    @Autowired
    SqsTemplate sqsTemplate;

    @Autowired
    ModelMapper modelMapper;

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
    public ResponseEntity<Postagem> recuperarPostagemPorId(@PathVariable("id") String id) {
        try {
            Optional<Postagem> postagem = postagemRepository.findById(UUID.fromString(id));
            return postagem.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/criacao/elogio/{idCliente}/{idEmpresa}")
    @ResponseBody
    public ResponseEntity<PostagemDTO> criarElogio(@Valid @RequestBody Elogio postagem,
                                              @PathVariable("idCliente") String idCliente,
                                              @PathVariable("idEmpresa") String idEmpresa) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(UUID.fromString(idCliente));
            if (cliente.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Empresa> empresa = empresaRepository.findById(UUID.fromString(idEmpresa));
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

            PostagemDTO postagemDTO = modelMapper.map(_postagem, PostagemDTO.class);

            sqsTemplate.send((o) -> o.queue("SQS-REVIEW-ADA.fifo")
                    .payload(postagemDTO));
            return new ResponseEntity<>(postagemDTO, HttpStatus.CREATED);
        } catch(Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/criacao/reclamacao/{idCliente}/{idEmpresa}")
    @ResponseBody
    public ResponseEntity<PostagemDTO> criarReclamacao(@Valid @RequestBody Reclamacao postagem,
                                                  @PathVariable("idCliente") String idCliente,
                                                  @PathVariable("idEmpresa") String idEmpresa) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(UUID.fromString(idCliente));
            if (cliente.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Empresa> empresa = empresaRepository.findById(UUID.fromString(idEmpresa));
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

            PostagemDTO postagemDTO = modelMapper.map(_postagem, PostagemDTO.class);

            sqsTemplate.send((o) -> o.queue("SQS-REVIEW-ADA.fifo")
                    .payload(postagemDTO));
            return new ResponseEntity<>(postagemDTO, HttpStatus.CREATED);
        } catch(Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Postagem> atualizarPorId(@PathVariable("id") String id,
                                                   @RequestBody Postagem postagem) {
        try {
            Optional<Postagem> resultado = postagemRepository.findById(UUID.fromString(id));
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
    public ResponseEntity<HttpStatus> removerPostagem(@PathVariable("id") String id) {
        try {
            postagemRepository.deleteById(UUID.fromString(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
