package dev.lucas.preview.controller;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.model.cadastro.Usuario;
import dev.lucas.preview.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuario")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<Usuario>> recuperarClientes() {
        try {
            List<Usuario> usuarios = new ArrayList<>(clienteRepository.findAll());
            if (usuarios.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> recuperarClientePorId(@PathVariable("id") int id) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cadastro")
    @ResponseBody
    public ResponseEntity<Cliente> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente usuario = clienteRepository.save(new Cliente(cliente.getNome(),
                    cliente.getEmail(), cliente.getDataNascimento()));
            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> atualizarPorId(@PathVariable("id") int id, @RequestBody  Cliente cliente) {
        try {
            Optional<Cliente> usuario = clienteRepository.findById(id);
            if (usuario.isPresent()) {
                Cliente _cliente = usuario.get();
                _cliente.setNome(cliente.getNome());
                _cliente.setEmail(cliente.getEmail());
                _cliente.setDataNascimento(cliente.getDataNascimento() != null
                        ? cliente.getDataNascimento()
                        : _cliente.getDataNascimento());

                return new ResponseEntity<>(clienteRepository.save(_cliente), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> removerCliente(@PathVariable("id") int id) {
        try {
            clienteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
