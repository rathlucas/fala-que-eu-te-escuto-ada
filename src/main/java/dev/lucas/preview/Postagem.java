package dev.lucas.preview;

import java.util.Date;

public sealed abstract class Postagem permits Elogio, Reclamacao {

    private String titulo;
    private String mensagem;
    private Empresa empresa;
    private Date criadoEm;

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

    @Override
    public String toString() {
        return "Postagem{" +
                "titulo='" + titulo + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", criadoEm=" + criadoEm +
                '}';
    }
}
