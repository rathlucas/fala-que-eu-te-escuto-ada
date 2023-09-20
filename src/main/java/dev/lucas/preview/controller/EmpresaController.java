package dev.lucas.preview.controller;

import dev.lucas.preview.model.cadastro.Empresa;
import dev.lucas.preview.repository.EmpresaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    @Autowired
    EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<List<Empresa>> recuperarEmpresas() {
        try {
            List<Empresa> empresas = new ArrayList<>(empresaRepository.findAll());
            if (empresas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(empresas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Empresa> recuperarEmpresaPorId(@PathVariable("id") int id) {
        try {
            Optional<Empresa> empresa = empresaRepository.findById(id);
            return empresa.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cadastro")
    @ResponseBody
    public ResponseEntity<Empresa> cadastrarEmpresa(@Valid @RequestBody Empresa empresa) {
        try {
            Empresa _empresa = empresaRepository.save(new Empresa(
                    empresa.getCnpj(),
                    empresa.getNomeFantasia()));
            return new ResponseEntity<>(_empresa, HttpStatus.CREATED);
        } catch(Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Empresa> atualizarPorId(@PathVariable("id") int id, @RequestBody  Empresa empresa) {
        try {
            Optional<Empresa> usuario = empresaRepository.findById(id);
            if (usuario.isPresent()) {
                Empresa _empresa = usuario.get();
                _empresa.setCnpj(empresa.getCnpj());
                _empresa.setNomeFantasia(empresa.getNomeFantasia());

                return new ResponseEntity<>(empresaRepository.save(_empresa), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> removerEmpresa(@PathVariable("id") int id) {
        try {
            empresaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
