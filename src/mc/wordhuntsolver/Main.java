package mc.wordhuntsolver;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {

        DictionaryTrie d = generateDictionary();

        Scanner r = new Scanner(System.in);
        System.out.print("Generate Random Board (1) \nGenerate Inputed Board (2) \nPick an Option: ");
        int choice = r.nextInt();
        if (!(choice == 1 || choice == 2)) {
            r.close();
            return;
        }
        System.out.print("Enter Height: ");
        int height = r.nextInt();
        if (height < 0) {
            r.close();
            return;
        }
        System.out.print("Enter Width: ");
        int width = r.nextInt();
        if (width < 0) {
            r.close();
            return;
        }

        GameBoard b;
        if (choice == 1) {
            b = generateRandomBoard(height, width);
        } else {
            System.out.print("Enter Letters: ");
            String s = r.next();
            if (s.length() != height * width) {
                r.close();
                return;
            }

            b = new GameBoard(s.toUpperCase().toCharArray(), height, width);
        }
        r.close();

        System.out.println(b);

        generateWordList(b, d);

        for (String s : b.getListWords()) {
            System.out.println(s);
        }

        System.out.println("Number of Words: " + b.getListWords().size());
    }

    private static void generateWordList(GameBoard b, DictionaryTrie d) {
        int h = b.getBoard().length;
        int w = b.getBoard()[0].length;

        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                b.searchBoard(d, d, "", r, c, new boolean[h][w]);
            }
        }

    }

    private static DictionaryTrie generateDictionary() throws IOException {
        Scanner r = new Scanner(new File("Collins Scrabble Words (2019).txt"));
        DictionaryTrie dic = new DictionaryTrie();
        while (r.hasNext()) {
            dic.add(r.nextLine());
        }
        return dic;
    }

    private static GameBoard generateRandomBoard(int boardHeight, int boardWidth) {
        char[] letters = randomLetters(boardHeight * boardWidth);
        return new GameBoard(letters, boardHeight, boardWidth);
    }

    private static char[] randomLetters(int size) {
        char[] c = new char[size];

        Random r = new Random();
        for (int i = 0; i < size; i++) {
            c[i] = (char) (r.nextInt(26) + 65);
            System.out.print(c[i]);
        }
        System.out.println();
        return c;

    }
}
