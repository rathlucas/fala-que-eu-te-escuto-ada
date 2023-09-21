package dev.lucas.preview.controller;

import dev.lucas.preview.model.postagem.Curtida;
import dev.lucas.preview.model.postagem.Postagem;
import dev.lucas.preview.repository.CurtidaRepository;
import dev.lucas.preview.repository.PostagemRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Curtidas", description = "Criação e manipulação de curtidas")
@RestController
@RequestMapping(value = "/api/v1/curtidas")
public class CurtidaController {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    CurtidaRepository curtidaRepository;

    @Autowired
    PostagemRepository postagemRepository;

    @GetMapping
    ResponseEntity<List<CurtidaDTO>> recuperarCurtidas() {
        try {
           List<Curtida> curtidas = curtidaRepository.findAll();
           if (curtidas.isEmpty()) {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }

           List<CurtidaDTO> curtidasDTO = new ArrayList<>();
           for (var curtida : curtidas) {
               CurtidaDTO curtidaDTO = modelMapper.map(curtida, CurtidaDTO.class);
               curtidasDTO.add(curtidaDTO);
           }

           return new ResponseEntity<>(curtidasDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping (value = "/{id}")
    ResponseEntity<CurtidaDTO> recuperarCurtidaPorId(@PathVariable("id") String id) {
        try {
            Optional<Curtida> curtida = curtidaRepository.findById(UUID.fromString(id));

            return curtida.map(value -> new ResponseEntity<>(modelMapper.map(value, CurtidaDTO.class), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{idPostagem}")
    ResponseEntity<CurtidaDTO> curtirPostagem(@PathVariable("idPostagem") String id,
                                           @RequestBody(required = false) String mensagem) {
        try {
            Optional<Postagem> postagem = postagemRepository.findById(UUID.fromString(id));
            if (postagem.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Postagem _postagem = postagem.get();
            Curtida curtida = new Curtida(_postagem, mensagem);

            _postagem.setCurtidas(List.of(curtida));
            curtidaRepository.save(curtida);
            postagemRepository.save(_postagem);

            return new ResponseEntity<>(modelMapper.map(curtida, CurtidaDTO.class), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<CurtidaDTO> atualizarPorId(@PathVariable("id") String id,
                                           @RequestBody Curtida curtida) {
        try {
            Optional<Curtida> _curtida = curtidaRepository.findById(UUID.fromString(id));
            if (_curtida.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            _curtida.get().setComentario(curtida.getComentario());
            curtidaRepository.save(_curtida.get());

            CurtidaDTO curtidaDTO = modelMapper.map(_curtida.get(), CurtidaDTO.class);

            return new ResponseEntity<>(curtidaDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<HttpStatus> removerCurtida(@PathVariable("id") String id) {
        try {
            curtidaRepository.deleteById(UUID.fromString(id));

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
