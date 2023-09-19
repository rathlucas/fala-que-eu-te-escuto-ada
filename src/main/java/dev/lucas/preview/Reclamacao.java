package dev.lucas.preview;

public final class Reclamacao extends Postagem implements Comparable<Postagem> {

    public Reclamacao() {}

    public Reclamacao(String titulo, String mensagem, Empresa empresa) {
        super(titulo, mensagem, empresa);
    }

    @Override
    public int compareTo(Postagem o) {
        return this.getCurtidas().size() - o.getCurtidas().size();
    }
}
