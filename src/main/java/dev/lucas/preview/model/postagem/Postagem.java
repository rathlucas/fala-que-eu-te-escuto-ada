package dev.lucas.preview.model.postagem;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.model.cadastro.Empresa;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public abstract class Postagem implements Comparable<Postagem> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String mensagem;

    @ManyToOne
    private Empresa empresa;

    @ManyToOne
    private Cliente cliente;

    @Column(nullable = false)
    private final Date criadoEm;

    @OneToMany
    private final List<Curtida> curtidas = new ArrayList<>();

    public Postagem() {
        this.criadoEm = new Date();
    }

    public Postagem(String titulo, String mensagem, Empresa empresa) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.empresa = empresa;
        this.criadoEm = new Date();
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

    public Date getCriadoEm() {
        return criadoEm;
    }

    public List<Curtida> getCurtidas() {
        return curtidas;
    }

    public void curtir(Curtida curtida) {
        curtidas.add(curtida);
        System.out.println("Curtida adicionada com sucesso!");
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
