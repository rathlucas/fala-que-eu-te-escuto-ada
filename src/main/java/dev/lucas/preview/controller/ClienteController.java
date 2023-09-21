package dev.lucas.preview.controller;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.repository.ClienteRepository;
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
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> recuperarClientes() {
        try {
            List<Cliente> clientes = new ArrayList<>(clienteRepository.findAll());
            if (clientes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<ClienteDTO> clientesDTO = new ArrayList<>();

            for (var cliente : clientes) {
                ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
                clientesDTO.add(clienteDTO);
            }

            return new ResponseEntity<>(clientesDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ClienteDTO> recuperarClientePorId(@PathVariable("id") String id) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(UUID.fromString(id));

            return cliente.map(value -> new ResponseEntity<>(
                    modelMapper.map(value, ClienteDTO.class), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cadastro")
    @ResponseBody
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente _cliente = clienteRepository.save(new Cliente(cliente.getNome(),
                    cliente.getEmail(), cliente.getDataNascimento()));

            return new ResponseEntity<>(modelMapper.map(_cliente, ClienteDTO.class), HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ClienteDTO> atualizarPorId(@PathVariable("id") int id,
                                                     @RequestBody  Cliente cliente) {
        try {
            Optional<Cliente> usuario = clienteRepository.findById(id);
            if (usuario.isPresent()) {
                Cliente _cliente = usuario.get();
                _cliente.setNome(cliente.getNome());
                _cliente.setEmail(cliente.getEmail());
                _cliente.setDataNascimento(cliente.getDataNascimento() != null
                        ? cliente.getDataNascimento()
                        : _cliente.getDataNascimento());

                clienteRepository.save(_cliente);
                ClienteDTO clienteDTO = modelMapper.map(_cliente, ClienteDTO.class);

                return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
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
