package dev.lucas.preview;

import java.util.LinkedList;
import java.util.List;

public class Timeline {
    private final LinkedList<Postagem> postagens;

    public Timeline() {
       this.postagens = new LinkedList<>();
    }

    public Timeline(List<Postagem> postagens) {
        this.postagens = new LinkedList<>(postagens);
    }

    public List<Postagem> getPostagens() {
        return postagens;
    }

    public void adicionarPostagem(Postagem postagem) {
        postagens.addFirst(postagem);
        System.out.println("Postagem adicionada a Timeline!");
    }

    public void removerPostagem(Postagem postagem) {
        postagens.remove(postagem);
        System.out.println("Postagem removida da Timeline!");
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "postagens=" + postagens +
                '}';
    }
}
