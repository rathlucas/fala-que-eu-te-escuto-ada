package dev.lucas.preview.model.postagem;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.model.cadastro.Empresa;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

    @OneToMany
    private final List<Curtida> curtidas = new ArrayList<>();

    @CreationTimestamp
    private Instant criadoEm;

    @UpdateTimestamp
    private Instant atualizadoEm;

    public Postagem() {
    }

    public Postagem(String titulo, String mensagem, Cliente cliente, Empresa empresa) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.empresa = empresa;
        this.cliente = cliente;
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

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public List<Curtida> getCurtidas() {
        return curtidas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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
