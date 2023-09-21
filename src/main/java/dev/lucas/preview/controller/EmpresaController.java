package dev.lucas.preview.controller;

import dev.lucas.preview.model.cadastro.Empresa;
import dev.lucas.preview.repository.EmpresaRepository;
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

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> recuperarEmpresas() {
        try {
            List<Empresa> empresas = new ArrayList<>(empresaRepository.findAll());
            if (empresas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<EmpresaDTO> empresasDTO = new ArrayList<>();

            for (var empresa : empresas) {
                EmpresaDTO empresaDTO = modelMapper.map(empresa, EmpresaDTO.class);
                empresasDTO.add(empresaDTO);
            }

            return new ResponseEntity<>(empresasDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<EmpresaDTO> recuperarEmpresaPorId(@PathVariable("id") String id) {
        try {
            Optional<Empresa> empresa = empresaRepository.findById(UUID.fromString(id));
            return empresa
                    .map(value -> new ResponseEntity<>(modelMapper.map(value, EmpresaDTO.class),
                            HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cadastro")
    @ResponseBody
    public ResponseEntity<EmpresaDTO> cadastrarEmpresa(@Valid @RequestBody Empresa empresa) {
        try {
            Empresa _empresa = empresaRepository.save(new Empresa(
                    empresa.getCnpj(),
                    empresa.getNomeFantasia(),
                    empresa.getEmail(), empresa.getArea()));

            EmpresaDTO empresaDTO = modelMapper.map(_empresa, EmpresaDTO.class);

            return new ResponseEntity<>(empresaDTO, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<EmpresaDTO> atualizarPorId(@PathVariable("id") int id,
                                                  @RequestBody Empresa empresa) {
        try {
            Optional<Empresa> usuario = empresaRepository.findById(id);
            if (usuario.isPresent()) {
                Empresa _empresa = usuario.get();
                _empresa.setCnpj(empresa.getCnpj());
                _empresa.setNomeFantasia(empresa.getNomeFantasia());
                _empresa.setEmail(empresa.getEmail());
                _empresa.setArea(empresa.getArea());
                empresaRepository.save(_empresa);

                EmpresaDTO empresaDTO = modelMapper.map(_empresa, EmpresaDTO.class);

                return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
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
