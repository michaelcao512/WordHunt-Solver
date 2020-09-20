package mc.wordhuntsolver;

import java.util.ArrayList;

public class GameBoard {
    private Tile[][] board;
    private ArrayList<String> listWords;

    final int[] dr = {-1, -1, -1, 0, 1, 1, 1, 0};
    final int[] dc = {-1, 0, 1, 1, 1, 0, -1, -1};

    GameBoard(char[] letters, int boardHeight, int boardLength) {

        board = new Tile[boardHeight][boardLength];
        listWords = new ArrayList<String>();
        for (int r = 0; r < boardHeight; r++) {
            for (int c = 0; c < boardLength; c++) {
                int currentIndex = r * boardLength + c;
                board[r][c] = new Tile(letters[currentIndex], r, c);
            }
        }
    }

    public Tile[][] getBoard() {
        return this.board;
    }

    public ArrayList<String> getListWords() {
        return this.listWords;
    }

    public void searchBoard(DictionaryTrie d, DictionaryTrie dc, String s, int r, int c, boolean[][] visited) {

        // base case: out of bounds or already visited
        if (r < 0 || c < 0 || r >= this.getBoard().length || c >= this.getBoard()[0].length) {
            return;
        }
        if (visited[r][c]) {
            return;
        }

        char currentLetter = board[r][c].getLetter();
        visited[r][c] = true;

        // checks if the letter exists as the next letter in trie dictionary
        if (d.getChild()[currentLetter - 65] != null) {
            s += String.valueOf(currentLetter);
            if (s.length() >= 3 && dc.isWord(s)) {

                if (!listWords.contains(s)) {
                    listWords.add(s);
                }
            }
            // recurses through adjacent tiles
            int[][] nextTile = getAdjacentTile(r, c);
            for (int i = 0; i < 8; i++) {
                int rr = nextTile[0][i];
                int cc = nextTile[1][i];
                searchBoard(d.getChild()[currentLetter - 65], dc, s, rr, cc, visited);
            }
        }
        visited[r][c] = false;
    }

    private int[][] getAdjacentTile(int r, int c) {
        int[][] nextTile = new int[2][8];

        for (int i = 0; i < 8; i++) {
            int rr = r + dr[i]; // row index of new tile
            int cc = c + dc[i]; // col index of new tile

            nextTile[0][i] = rr;
            nextTile[1][i] = cc;
        }

        return nextTile;
    }

    public String toString() {
        String s = "";
        for (int r = 0; r < this.board.length; r++) {
            for (int c = 0; c < this.board[0].length; c++) {
                s += this.board[r][c].getLetter() + " ";
            }
            s += "\n";
        }
        return s;
    }

}
