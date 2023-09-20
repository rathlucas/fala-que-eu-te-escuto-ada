package dev.lucas.preview.model.postagem;

import dev.lucas.preview.model.cadastro.Empresa;

public class Elogio extends Postagem implements Comparable<Postagem>{

    public Elogio() {}

    public Elogio(String titulo, String mensagem, Empresa empresa) {
        super(titulo, mensagem, empresa);
    }

    @Override
    public int compareTo(Postagem o) {
        return o.getCurtidas().size() - this.getCurtidas().size();
    }
}
