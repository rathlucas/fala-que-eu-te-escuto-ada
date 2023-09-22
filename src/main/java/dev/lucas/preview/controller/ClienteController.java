package dev.lucas.preview.controller;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Consulta e manipulação de clientes")
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> recuperarClientes() {
            var resposta = clienteService.recuperarTodos();
            return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ClienteDTO> recuperarClientePorId(@PathVariable("id") String id) {
        try {
            ClienteDTO cliente = clienteService.recuperarUm(id);
            if (cliente == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cadastro")
    @ResponseBody
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        try {
            ClienteDTO _cliente = clienteService.criar(cliente);
            return new ResponseEntity<>(_cliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ClienteDTO> atualizarPorId(@PathVariable("id") String id,
                                                     @RequestBody Cliente payload) {
        try {
            ClienteDTO clienteDTO = clienteService.atualizar(id, payload);
            if (clienteDTO == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> removerCliente(@PathVariable("id") String id) {
        try {
            clienteService.remover(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
