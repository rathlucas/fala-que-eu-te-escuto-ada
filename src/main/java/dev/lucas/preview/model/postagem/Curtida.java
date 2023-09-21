package dev.lucas.preview.model.postagem;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Nullable
    @Size(min = 3, max = 500)
    @Column(nullable = true)
    private String comentario;

    @ManyToOne
    @JsonIdentityInfo(generator =
            ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Postagem postagem;

    @CreationTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant criadoEm;

    @UpdateTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant atualizadoEm;

    public Curtida() {
    }

    public Curtida(Postagem postagem, @Nullable String comentario) {
        this.postagem = postagem;
        this.comentario = comentario;
    }

    public UUID getId() {
        return id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(@Nullable String comentario) {
        this.comentario = comentario;
    }

    public Postagem getPostagem() {
        return postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    @Override
    public String toString() {
        return "Curtida{" +
                "id=" + id +
                ", comentario='" + comentario + '\'' +
                ", criadoEm=" + criadoEm +
                ", atualizadoEm=" + atualizadoEm +
                '}';
    }
}
