package dev.lucas.preview.controller;

import java.time.Instant;
import java.util.UUID;

public class CurtidaDTO {

    private UUID id;
    private String comentario;
    private Instant criadoEm;

    public CurtidaDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }
}
