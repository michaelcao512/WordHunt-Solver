package mc.wordhuntsolver;

public class Tile {
    private char letter;
    private int row;
    private int col;

    Tile(char l, int r, int c) {
        this.letter = l;
        this.row = r;
        this.col = c;
    }

    public char getLetter() {
        return this.letter;
    }
}

