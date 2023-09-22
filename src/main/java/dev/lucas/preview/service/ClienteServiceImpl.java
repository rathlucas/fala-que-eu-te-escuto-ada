package dev.lucas.preview.service;

import dev.lucas.preview.controller.ClienteDTO;
import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ModelMapper modelMapper, ClienteRepository clienteRepository) {
        this.modelMapper = modelMapper;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<ClienteDTO> recuperarTodos() {
        List<Cliente> clientes = new ArrayList<>(clienteRepository.findAll());
        if (clientes.isEmpty()) {
            return null;
        }

        List<ClienteDTO> clientesDTO = new ArrayList<>();

        for (var cliente : clientes) {
            ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
            clientesDTO.add(clienteDTO);
        }

        return clientesDTO;
    }

    @Override
    public ClienteDTO recuperarUm(String id) {
        Optional<Cliente> cliente = clienteRepository.findById(UUID.fromString(id));
        return cliente.map(value -> modelMapper.map(value, ClienteDTO.class)).orElse(null);
    }

    @Override
    public ClienteDTO criar(Cliente cliente) {
        try {
            Cliente _cliente = clienteRepository.save(new Cliente(cliente.getNome(),
                    cliente.getEmail(), cliente.getDataNascimento()));

            return modelMapper.map(_cliente, ClienteDTO.class);
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }
    }

    @Override
    public ClienteDTO atualizar(String id, Cliente payload) {
        Optional<Cliente> usuario = clienteRepository.findById(UUID.fromString(id));
        if (usuario.isPresent()) {
            Cliente _cliente = usuario.get();
            _cliente.setNome(payload.getNome());
            _cliente.setEmail(payload.getEmail());
            _cliente.setDataNascimento(payload.getDataNascimento() != null
                    ? payload.getDataNascimento()
                    : _cliente.getDataNascimento());

            clienteRepository.save(_cliente);
            return modelMapper.map(_cliente, ClienteDTO.class);
        }

        return null;
    }

    @Override
    public void remover(String id) {
        clienteRepository.deleteById(UUID.fromString(id));
    }
}
