package dev.lucas.preview.model.postagem;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.model.cadastro.Empresa;
import jakarta.persistence.Entity;

@Entity
public final class Elogio extends Postagem implements Comparable<Postagem>{

    public Elogio() {}

    public Elogio(String titulo, String mensagem, Cliente cliente, Empresa empresa) {
        super(titulo, mensagem, cliente, empresa);
    }

    @Override
    public int compareTo(Postagem o) {
        return o.getCurtidas().size() - this.getCurtidas().size();
    }
}
