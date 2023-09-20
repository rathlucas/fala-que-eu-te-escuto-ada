package dev.lucas.preview.model.postagem;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = true)
    private String comentario;

    @Column(nullable = false)
    private final LocalDateTime criadoEm;

    public Curtida() {
        this.criadoEm = LocalDateTime.now();
    }

    public Curtida(String comentario) {
        this.comentario = comentario;
        this.criadoEm = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Curtida{" +
                "comentario='" + comentario + '\'' +
                ", criadoEm=" + criadoEm +
                '}';
    }
}
