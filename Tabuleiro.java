package Puzzle_Quest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Tabuleiro {
    private char[][] matriz;
    private Random gerador;
    public boolean turnoExtra = false;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_ORANGE = "\u001B[38;5;214m";

    public Tabuleiro() {
        matriz = new char[8][8];
        gerador = new Random();
        inicializaTabuleiro();
    }

    private void inicializaTabuleiro() {
        char[] letras = { 'S', 'G', 'R', 'B', 'T', 'P', 'Y' };
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char novoChar;
                do {
                    novoChar = letras[gerador.nextInt(letras.length)];
                } while (causariaGrupoDeTres(i, j, novoChar));
                matriz[i][j] = novoChar;
            }
        }
    }

    private boolean causariaGrupoDeTres(int linha, int coluna, char c) {
        if (coluna >= 2 && matriz[linha][coluna - 1] == c && matriz[linha][coluna - 2] == c) {
            return true;
        }
        if (linha >= 2 && matriz[linha - 1][coluna] == c && matriz[linha - 2][coluna] == c) {
            return true;
        }
        return false;
    }

    public void moverLetras(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        char temp = matriz[linhaOrigem - 1][colunaOrigem - 1];
        matriz[linhaOrigem - 1][colunaOrigem - 1] = matriz[linhaDestino - 1][colunaDestino - 1];
        matriz[linhaDestino - 1][colunaDestino - 1] = temp;
    }

    public boolean verificaGrupos() {
        boolean gruposEncontrados = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                if (verificaGrupoHorizontal(i, j) || verificaGrupoVertical(i, j)) {
                    gruposEncontrados = true;
                }
            }
        }
        return gruposEncontrados;
    }

    private boolean verificaGrupoHorizontal(int linha, int coluna) {
        if (coluna <= 5 && matriz[linha][coluna] == matriz[linha][coluna + 1]
                && matriz[linha][coluna] == matriz[linha][coluna + 2]) {
            return true;
        }
        return false;
    }

    private boolean verificaGrupoVertical(int linha, int coluna) {
        if (linha <= 5 && matriz[linha][coluna] == matriz[linha + 1][coluna]
                && matriz[linha][coluna] == matriz[linha + 2][coluna]) {
            return true;
        }
        return false;
    }

    public void removerGrupos(Jogador jogadorAtual, Jogador adversario) {
        List<int[]> letrasARemover = new ArrayList<>();
        Set<Character> letrasEncontradas = new HashSet<>();

        // Marcar os grupos horizontais e verticais para remoção
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char letraAtual = matriz[i][j];
                if (letraAtual != '-') {
                    // Verificar grupo horizontal
                    if (j <= 5 && matriz[i][j] == matriz[i][j + 1] && matriz[i][j] == matriz[i][j + 2]) {
                        letrasARemover.add(new int[] { i, j });
                        letrasARemover.add(new int[] { i, j + 1 });
                        letrasARemover.add(new int[] { i, j + 2 });
                        letrasEncontradas.add(letraAtual);
                    }
                    if (j <= 4 && matriz[i][j] == matriz[i][j + 1] && matriz[i][j] == matriz[i][j + 2] && matriz[i][j] == matriz[i][j + 3]){
                        turnoExtra = true;
                        letrasARemover.add(new int[] { i, j + 3 });
                    }
                    // Verificar grupo vertical
                    if (i <= 5 && matriz[i][j] == matriz[i + 1][j] && matriz[i][j] == matriz[i + 2][j]) {
                        letrasARemover.add(new int[] { i, j });
                        letrasARemover.add(new int[] { i + 1, j });
                        letrasARemover.add(new int[] { i + 2, j });
                        letrasEncontradas.add(letraAtual);

                    }
                    if (i <= 4 && matriz[i][j] == matriz[i + 1][j] && matriz[i][j] == matriz[i + 2][j] && matriz[i][j] == matriz[i + 3][j]){
                        turnoExtra = true;
                        letrasARemover.add(new int[] { i + 3, j });
                    }
                }
            }
        }

        // Remover as letras e aplicar os efeitos
        for (int[] pos : letrasARemover) {
            matriz[pos[0]][pos[1]] = '-';
        }

        for (char letra : letrasEncontradas) {
            switch (letra) {
                case 'R':
                    jogadorAtual.setHp(jogadorAtual.getHp() + 3);
                    System.out.println("Efeito aplicado: +3 HP para o jogador atual");
                    break;
                case 'B':
                    substituirLetra(matriz, 'R', 'S');
                    System.out.println("Efeito aplicado: Todas as letras 'R' foram substituídas por 'S'");
                    break;
                case 'G':
                    substituirLetra(matriz, 'S', 'R');
                    System.out.println("Efeito aplicado: Todas as letras 'S' foram substituídas por 'R'");
                    break;
                case 'S':
                    adversario.setHp(adversario.getHp() - 3);
                    System.out.println("Efeito aplicado: -3 HP para o adversário");
                    if (jogadorAtual.getOuro() == 10) {
                        adversario.setHp(adversario.getHp() - 3);
                        jogadorAtual.setOuro(0);
                        System.out.println(
                                "Efeito adicional aplicado: -3 HP extra para o adversário e reset do ouro do jogador atual");
                    }
                    break;
                case 'P':
                    jogadorAtual.setXp(jogadorAtual.getXp() + 2);
                    System.out.println("Efeito aplicado: +2 XP para o jogador atual");
                    if (jogadorAtual.getXp() == 10) {
                        adversario.setHp(adversario.getHp() - 9);
                        jogadorAtual.setXp(0);
                        System.out.println(
                                "Efeito adicional aplicado: -9 HP para o adversário e reset do XP do jogador atual");
                    }
                    break;
                case 'Y':
                    adversario.setOuro(0);
                    System.out.println("Efeito aplicado: Ouro do adversário resetado");
                    break;
                case 'T':
                    jogadorAtual.setOuro(jogadorAtual.getOuro() + 2);
                    System.out.println("Efeito aplicado: +2 Ouro para o jogador atual");
                    break;
            }
        }
    }

    public void compactarTabuleiro() {
        for (int coluna = 0; coluna < 8; coluna++) {
            int destino = 7;
            for (int linha = 7; linha >= 0; linha--) {
                if (matriz[linha][coluna] != '-') {
                    matriz[destino][coluna] = matriz[linha][coluna];
                    destino--;
                }
            }
            while (destino >= 0) {
                matriz[destino][coluna] = gerarNovaLetra();
                destino--;
            }
        }
    }

    private char gerarNovaLetra() {
        char[] letras = { 'S', 'G', 'R', 'B', 'T', 'P', 'Y' };
        return letras[gerador.nextInt(letras.length)];
    }

    public void exibeTabuleiro() {
        System.out.println("Tabuleiro:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char letra = matriz[i][j];
                String cor = obterCorPorLetra(letra);
                System.out.print(cor + letra + ANSI_RESET + "\t");
            }
            System.out.println();
        }
    }
    private String obterCorPorLetra(char letra) {
        switch (letra) {
            case 'S':
                return ANSI_WHITE;
            case 'G':
                return ANSI_GREEN;
            case 'T':
                return ANSI_ORANGE;
            case 'R':
                return ANSI_RED;
            case 'Y':
                return ANSI_YELLOW;
            case 'B':
                return ANSI_BLUE;
            case 'P':
                return ANSI_PURPLE;
            default:
                return ANSI_RESET;
        }
    }

    public static void substituirLetra(char[][] matriz, char antigaLetra, char novaLetra) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == antigaLetra) {
                    matriz[i][j] = novaLetra;
                }
            }
        }
    }
}