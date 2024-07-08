package Puzzle_Quest;
//linha 12, 14, 15 adicionei variaveis para ouro e para experiencia
// tirei o main que tava nessa pagina
// coloquei metodo try na linha 23 (pq o vscode pediu kksksks)

import java.util.Scanner;

public class Partida {
    private Tabuleiro tabuleiro;
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorAtual;
    private int rodadas;

    public Partida(String nomeJogador1, String nomeJogador2, int hpInicial, int ouroInicial, int xpInicial) {
        tabuleiro = new Tabuleiro();
        jogador1 = new Jogador(nomeJogador1, hpInicial, ouroInicial, xpInicial);
        jogador2 = new Jogador(nomeJogador2, hpInicial, ouroInicial, xpInicial);
        jogadorAtual = jogador1;
        rodadas = 0;
    }

    public void iniciar() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (jogador1.getHp() > 0 && jogador2.getHp() > 0) {
                rodadas++;
                System.out.println("\n--- Rodada " + rodadas + " ---");
                tabuleiro.exibeTabuleiro();
                System.out.println(jogador1);
                System.out.println(jogador2);
                System.out.println("É a vez de " + jogadorAtual.getNome());

                System.out.println("Digite a linha origem (1 a 8):");
                int linhaOrigem = scanner.nextInt();
                System.out.println("Digite a coluna origem (1 a 8):");
                int colunaOrigem = scanner.nextInt();
                System.out.println("Digite a linha destino (1 a 8):");
                int linhaDestino = scanner.nextInt();
                System.out.println("Digite a coluna destino (1 a 8):");
                int colunaDestino = scanner.nextInt();

                tabuleiro.moverLetras(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);

                while (tabuleiro.verificaGrupos()) {
                    System.out.println("Grupos encontrados!");
                    tabuleiro.removerGrupos(jogadorAtual, (jogadorAtual == jogador1) ? jogador2 : jogador1);
                    tabuleiro.compactarTabuleiro();
                }

                // Passar a vez para o próximo jogador
                if (tabuleiro.turnoExtra == false){
                    jogadorAtual = (jogadorAtual == jogador1) ? jogador2 : jogador1;
                } else {
                    jogadorAtual = (jogadorAtual == jogador1) ? jogador1 : jogador2;
                    tabuleiro.turnoExtra = false;
                    System.out.println(jogadorAtual.getNome() + ", joga novamente");
                }


            }
        }
        // Exibir resultado final
        System.out.println("\n--- Fim de Jogo ---");
        if (jogador1.getHp() <= 0) {
            System.out.println(jogador2.getNome() + " ganhou!");
        } else {
            System.out.println(jogador1.getNome() + " ganhou!");
        }
    }

    public int getRodadas() {
        return rodadas;
    }

}
