package dev.lucas.preview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public sealed abstract class Postagem implements Comparable<Postagem> permits Elogio, Reclamacao  {

    private String titulo;
    private String mensagem;
    private Empresa empresa;
    private Date criadoEm;
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
