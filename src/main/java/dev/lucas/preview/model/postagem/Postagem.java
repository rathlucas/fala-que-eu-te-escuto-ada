package dev.lucas.preview.model.postagem;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.model.cadastro.Empresa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Postagem implements Comparable<Postagem> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotNull(message = "O campo titulo é obrigatorio")
    @Size(min = 5, max = 30,
            message = "O campo titulo deve possuir no mínimo 5 caracteres!")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "O campo mensagem é obrigatório!")
    @Size(max = 2000, message = "O tamanho máximo de uma mensagem é 2000 caracteres!")
    @Column(nullable = false)
    private String mensagem;

    @ManyToOne
    @JsonIdentityInfo(generator =
            ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Empresa empresa;

    @JsonIdentityInfo(generator =
            ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Cliente cliente;

    @JsonIdentityInfo(generator =
            ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "postagem")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Curtida> curtidas = new ArrayList<>();

    @CreationTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant criadoEm;

    @UpdateTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant atualizadoEm;

    public Postagem() {
    }

    public Postagem(String titulo, String mensagem, Cliente cliente, Empresa empresa) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.empresa = empresa;
        this.cliente = cliente;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public Instant getCriadoEm() {
        return criadoEm;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public List<Curtida> getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(List<Curtida> curtidas) {
        this.curtidas = curtidas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


    public abstract int compareTo(Postagem o);

    @Override
    public String toString() {
        return "Postagem{" +
                "titulo='" + titulo + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", empresa=" + empresa +
                ", criadoEm=" + criadoEm +
                ", curtidas=" + curtidas.size() +
                '}';
    }
}
