package dev.lucas.preview.model.postagem;

import dev.lucas.preview.model.cadastro.Empresa;

public class Reclamacao extends Postagem implements Comparable<Postagem> {

    public Reclamacao() {}

    public Reclamacao(String titulo, String mensagem, Empresa empresa) {
        super(titulo, mensagem, empresa);
    }

    @Override
    public int compareTo(Postagem o) {
        return this.getCurtidas().size() - o.getCurtidas().size();
    }
}
