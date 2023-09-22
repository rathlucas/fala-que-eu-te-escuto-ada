package dev.lucas.preview.model.cadastro;

import dev.lucas.preview.model.postagem.Elogio;
import dev.lucas.preview.model.postagem.Postagem;
import dev.lucas.preview.model.postagem.Reclamacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Entity
public final class Cliente extends Usuario implements Idade, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotNull(message = "O campo nome não pode ser nulo!")
    @Size(min = 3, max = 40, message = "O nome precisa estar entre 3 e 40 caracteres!")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O campo email não pode ser nulo!")
    @Column(nullable = false)
    @Email(message = "Email inválido inserido!")
    private String email;

    @NotNull(message = "O campo dataNascimento não pode ser nulo!")
    @Past
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "cliente")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Postagem> postagens;

    @CreationTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant criadoEm;

    @UpdateTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant atualizadoEm;

    @Override
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public int getIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

    public Cliente(){
    }

    public Cliente(String nome, String email, LocalDate dataNascimento){
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Postagem> getPostagens() {
        return postagens;
    }

    public void setPostagens(List<Postagem> postagens) {
        this.postagens = postagens;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public Elogio criarElogio(String titulo, String mensagem, Cliente cliente, Empresa empresa) {
        Elogio elogio = new Elogio(titulo, mensagem, cliente, empresa);
        postagens.add(elogio);
        return elogio;
    }

    public Reclamacao criarReclamacao(String titulo, String mensagem, Cliente cliente, Empresa empresa) {
        Reclamacao reclamacao = new Reclamacao(titulo, mensagem, cliente, empresa);
        postagens.add(reclamacao);
        return reclamacao;
    }

    public Postagem removerPostagem() {
        Postagem removida = postagens.remove(postagens.size() - 1);
        System.out.printf("%s removida com sucesso!%n", removida);
        return removida;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
