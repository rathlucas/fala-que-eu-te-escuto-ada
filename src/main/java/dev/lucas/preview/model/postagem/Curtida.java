package dev.lucas.preview.model.postagem;

import java.util.Date;

public class Curtida {

    private String comentario;
    private final Date criadoEm;

    public Curtida() {
        this.criadoEm = new Date();
    }

    public Curtida(String comentario) {
        this.comentario = comentario;
        this.criadoEm = new Date();
    }

    @Override
    public String toString() {
        return "Curtida{" +
                "comentario='" + comentario + '\'' +
                ", criadoEm=" + criadoEm +
                '}';
    }
}
