//Adicionei int ouro e xp e seus respectivos gets e setters

package Puzzle_Quest;
public class Jogador {
    private String nome;
    private int hp;
    private int ouro;
    private int xp;

    public Jogador(String nome, int hp, int ouro, int xp) {
        this.nome = nome;
        this.hp = hp;
        this.ouro = ouro;
        this.xp = xp;
    }

    public String getNome() {
        return nome;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getOuro() {
        return ouro;
    }

    public void setOuro(int ouro) {
        this.ouro = ouro;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public String toString() {
        return nome + " - HP: " + hp + " - Ouro: " + ouro + " - XP: " + xp;
    }
}
