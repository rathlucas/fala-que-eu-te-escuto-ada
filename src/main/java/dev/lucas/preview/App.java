package dev.lucas.preview;

public class App {
    public static void main(String[] args) {

        Timeline timeline = new Timeline();
        Cliente cliente = new Cliente();
        Empresa empresa = new Empresa();

        timeline.adicionarPostagem(cliente.criarElogio("Otimo atendimento!",
                "Foram muito atenciosos", empresa));
        System.out.println(cliente.getPostagens());
        System.out.println(timeline);


        timeline.removerPostagem(cliente.removerPostagem());
        System.out.println(cliente.getPostagens());
        System.out.println(timeline);

        timeline.adicionarPostagem(cliente.criarReclamacao("Pessimo!",
                "Odiei o serviço", empresa));
        System.out.println(cliente.getPostagens());
        System.out.println(timeline);

        for (var postagem : timeline.getPostagens()) {
            System.out.println(postagem);
        }
    }
}

/*

[x] Altere a modelagem para limitar os filhos possíveis de Postagem e Usuário
[x] Implemente a pilha de postagens para o Cliente
[x] Implemente uma timeline com as postagens como lista ligada
[] Altere a modelagem para adicionar a funcionalidade de “curtida” numa postagem de modo
 que o seja possível ordenar postagens com muito engajamento no topo

 */