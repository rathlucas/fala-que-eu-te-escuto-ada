package dev.lucas.preview.model.cadastro;

import dev.lucas.preview.model.postagem.Postagem;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public final class Empresa extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer cnpj;

    @Column(nullable = false)
    private String nomeFantasia;

    @OneToMany
    private final List<Postagem> postagens = new ArrayList<>();

}
