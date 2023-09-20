package dev.lucas.preview.model;

import dev.lucas.preview.model.Postagem;

import java.util.Collections;
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
        postagens.add(postagem);
        System.out.println("Postagem adicionada a Timeline!");
    }

    public void removerPostagem(Postagem postagem) {
        boolean resultado = postagens.remove(postagem);
        if (resultado) {
            System.out.println("Postagem removida da Timeline!");
        } else {
            System.err.println("Postagem não encontrada para remoção!");
        }
    }

    public List<Postagem> getPostagensMaisCurtidas() {
        List<Postagem> postagensMaisCurtidas = new LinkedList<>(postagens);
        Collections.sort(postagensMaisCurtidas);
        return postagensMaisCurtidas;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "postagens=" + postagens +
                '}';
    }
}
