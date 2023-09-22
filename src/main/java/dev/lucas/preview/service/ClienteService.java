package dev.lucas.preview.service;

import dev.lucas.preview.controller.ClienteDTO;
import dev.lucas.preview.model.cadastro.Cliente;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> recuperarTodos();

    ClienteDTO recuperarUm(String id);

    ClienteDTO criar(Cliente cliente);

    ClienteDTO atualizar(String id, Cliente payload);

    void remover(String id);
}
