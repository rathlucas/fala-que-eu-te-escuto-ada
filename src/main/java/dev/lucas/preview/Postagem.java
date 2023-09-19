package dev.lucas.preview;

import java.util.Date;

public sealed abstract class Postagem permits Elogio, Reclamacao {

    private String titulo;
    private String mensagem;
    private Date criadoEm;

    public Postagem() {
        this.criadoEm = new Date();
    }

    public Postagem(String titulo, String mensagem) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.criadoEm = new Date();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    @Override
    public String toString() {
        return "Postagem{" +
                "titulo='" + titulo + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", criadoEm=" + criadoEm +
                '}';
    }
}
