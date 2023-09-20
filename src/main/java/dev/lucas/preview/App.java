package dev.lucas.preview;

import dev.lucas.preview.model.cadastro.Cliente;
import dev.lucas.preview.model.postagem.Curtida;
import dev.lucas.preview.model.cadastro.Empresa;
import dev.lucas.preview.model.postagem.Timeline;

import java.time.LocalDate;
import java.time.Month;

public class App {

    private static final Timeline timeline = new Timeline();

    public static void main(String[] args) {

        Cliente cliente = new Cliente("Lucas",
                "lucin189@gmail.com", LocalDate.of(2000, Month.AUGUST, 29));
        Empresa empresa = new Empresa();

        timeline.adicionarPostagem(cliente.criarElogio("Otimo atendimento!",
                "Foram muito atenciosos", cliente, empresa));
        System.out.println(cliente.getPostagens());
        System.out.println(timeline);


        timeline.removerPostagem(cliente.removerPostagem());
        System.out.println(cliente.getPostagens());
        System.out.println(timeline);

        timeline.adicionarPostagem(cliente.criarReclamacao("Pessimo!",
                "Odiei o serviço", cliente, empresa));
        System.out.println(cliente.getPostagens());
        System.out.println(timeline);

        for (var postagem : timeline.getPostagens()) {
            System.out.println(postagem);
        }

        var elogio1 = cliente.criarElogio("Gostei!",
                "Foram muito atenciosos", cliente, empresa);
        elogio1.curtir(new Curtida());

        timeline.adicionarPostagem(elogio1);

        var elogio2 = cliente.criarElogio("Daora o atendimento!",
                "Foram muito bacanas", cliente, empresa);
        elogio2.curtir(new Curtida("Top!"));
        elogio2.curtir(new Curtida());
        elogio2.curtir(new Curtida());

        timeline.adicionarPostagem(elogio2);

        var elogio3 = cliente.criarElogio("Otimo atendimento!",
                "Foram muito atenciosos", cliente, empresa);

        timeline.adicionarPostagem(elogio3);

        System.out.println(timeline.getPostagensMaisCurtidas());
    }
}

/*

[x] Altere a modelagem para limitar os filhos possíveis de Postagem e Usuário
[x] Implemente a pilha de postagens para o Cliente
[x] Implemente uma timeline com as postagens como lista ligada
[x] Altere a modelagem para adicionar a funcionalidade de “curtida” numa postagem de modo
 que o seja possível ordenar postagens com muito engajamento no topo

 */