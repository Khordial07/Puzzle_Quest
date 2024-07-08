package Puzzle_Quest;

import java.util.Scanner;

public class Menu {

    public void mostraMenu(){
        System.out.println("================================"); // Menu simples
        System.out.println("Bem vindo(a) ao Puzzle Quest");
        System.out.println("");
        System.out.println("Digite uma das opções:");
        System.out.println("1 - Começar novo jogo");
        System.out.println("2 - Sair");
        System.out.println("================================");
    }
    Partida partida = new Partida("Jogador1", "Jogador2", 20, 0, 0);
    public void Executa(){
        Scanner leitor = new Scanner(System.in);
        mostraMenu();

        int opcao = leitor.nextInt();
        switch (opcao){ //Pede para o usuário digitar uma das opções

            case 1:
                partida.iniciar();
                break;
            case 2:
                System.out.println("Saindo do jogo...");
                System.exit(0); // código que fecha o programa
                break;
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();

        menu.Executa();
    }
}


