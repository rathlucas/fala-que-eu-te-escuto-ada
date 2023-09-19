package dev.lucas.preview;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public final class Cliente extends Usuario implements Idade {

    private final UUID uuid;
    private String nome;
    private String email;
    private LocalDate dataNascimento;

    @Override
    public int getIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

    private UUID gerarUuid(){
        return UUID.randomUUID();
    }

    public Cliente(){
        this.uuid = gerarUuid();
    }

    public Cliente(String nome, String email, LocalDate dataNascimento){
        this.uuid = gerarUuid();
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }


    public UUID getUuid() {
        return uuid;
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

    @Override
    public String toString() {
        return "Cliente{" +
                "uuid=" + uuid +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
