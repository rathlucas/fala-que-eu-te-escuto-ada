package dev.lucas.preview.model.cadastro;

import dev.lucas.preview.model.postagem.Postagem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public final class Empresa extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Pattern(regexp = "(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)",
            message = "Formato inválido de cnpj enviado!")
    @Column(nullable = false)
    private String cnpj;

    @Size(min = 4, max = 50,
            message = "O campo nomeFantasia precisa ter no mínimo 4 caracteres!")
    @Column(nullable = false)
    private String nomeFantasia;

    @NotNull(message = "O campo email é obrigatório!")
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull(message = "O campo area é obrigatório!")
    @Column(nullable = false)
    private String area;

    @OneToMany(mappedBy = "empresa")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private final List<Postagem> postagens = new ArrayList<>();

    @CreationTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant criadoEm;

    @UpdateTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant atualizadoEm;

    public Empresa() {
    }

    public Empresa(String cnpj, String nomeFantasia, String email, String area) {
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.email = email;
        this.area = area;
    }

    public UUID getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {return area;}

    public void setArea(String area) {
        this.area = area;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

}
