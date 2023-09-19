package dev.lucas.preview;

public class App {
    public static void main(String[] args) {

        Cliente cliente = new Cliente();

        cliente.criarElogio("Otimo atendimento!", "Foram muito atenciosos");
        System.out.println(cliente.getPostagens());

        cliente.removerPostagem();
        System.out.println(cliente.getPostagens());
    }
}

/*

[x] Altere a modelagem para limitar os filhos possíveis de Postagem e Usuário
[] Implemente a pilha de postagens para o Cliente
[] Implemente uma timeline com as postagens como lista ligada
[] Altere a modelagem para adicionar a funcionalidade de “curtida” numa postagem de modo
 que o seja possível ordenar postagens com muito engajamento no topo

 */