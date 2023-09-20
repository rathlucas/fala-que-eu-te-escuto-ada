package dev.lucas.preview.model.cadastro;

import dev.lucas.preview.model.postagem.Postagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Component
public final class Empresa extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Pattern(regexp = "(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)")
    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String nomeFantasia;

    @OneToMany
    private final List<Postagem> postagens = new ArrayList<>();

    public Empresa(){
    }

    public Empresa(String cnpj, String nomeFantasia) {
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public List<Postagem> getPostagens() {
        return postagens;
    }
}
