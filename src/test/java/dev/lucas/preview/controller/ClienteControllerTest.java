package dev.lucas.preview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.service.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClienteController.class)
@ExtendWith(SpringExtension.class)
public class ClienteControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteServiceImpl clienteService;

    public static Cliente getCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("teste");
        cliente.setEmail("teste@mail.com");
        cliente.setDataNascimento(LocalDate.parse("2000-08-29"));

        return cliente;
    }

    public static ClienteDTO getClienteDTO() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setId(UUID.randomUUID());
        cliente.setNome("teste");
        cliente.setEmail("teste@mail.com");
        cliente.setDataNascimento(LocalDate.parse("2000-08-29"));
        cliente.setCriadoEm(Instant.now());

        return cliente;
    }

    @Test
    public void get_deveRetornarTodosOsClientes() throws Exception {
        ClienteDTO cliente2 = getClienteDTO();

        List<ClienteDTO> listaMock = new ArrayList<>();
        listaMock.add(cliente2);
        listaMock.add(cliente2);

        when(clienteService.recuperarTodos()).thenReturn(listaMock);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/clientes")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn();
    }

    @Test
    public void get_deveRetornarUmCliente() throws Exception {
        ClienteDTO cliente = getClienteDTO();

        when(clienteService.recuperarUm(any())).thenReturn(cliente);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/clientes/{id}", cliente.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId().toString()))
                .andReturn();
    }

    @Test
    public void get_deveRetornar404SemCliente() throws Exception {
        when(clienteService.recuperarUm(any())).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/clientes/{id}", UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void get_deveRetornar500SemId() throws Exception {
        when(clienteService.recuperarUm(any())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/clientes/{id}", UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void post_deveCadastrarUmCliente() throws Exception {
        Cliente cliente = getCliente();
        ClienteDTO clienteDTO = getClienteDTO();

        when(clienteService.criar(cliente)).thenReturn(clienteDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/clientes/cadastro")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().is(201))
                .andReturn();
    }

    @Test
    public void post_deveRetornar400SemPayload() throws Exception {
        when(clienteService.criar(new Cliente())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/clientes/cadastro")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void deveAtualizarUmCliente() throws Exception {
        Cliente payload = getCliente();
        payload.setNome("Xesque");
        payload.setEmail("xesque@deles.com");

        ClienteDTO clienteDTO = getClienteDTO();
        clienteDTO.setId(UUID.randomUUID());
        clienteDTO.setNome(payload.getNome());
        clienteDTO.setEmail(payload.getEmail());

        when(clienteService.atualizar(any(), any())).thenReturn(clienteDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/clientes/{id}", clienteDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteDTO.getId().toString()))
                .andReturn();
    }

    @Test
    public void deveRemoverUmCliente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clientes/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204))
                .andReturn();
    }

}
