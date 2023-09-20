package dev.lucas.preview.model;

import dev.lucas.preview.model.cadastro.Empresa;

public final class Elogio extends Postagem implements Comparable<Postagem>{

    public Elogio() {}

    public Elogio(String titulo, String mensagem, Empresa empresa) {
        super(titulo, mensagem, empresa);
    }

    @Override
    public int compareTo(Postagem o) {
        return o.getCurtidas().size() - this.getCurtidas().size();
    }
}
