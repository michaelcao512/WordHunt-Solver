package mc.wordhuntsolver;

public class DictionaryTrie {

    private DictionaryTrie[] child;
    private boolean word;
    private char character;

    DictionaryTrie() {
        this.child = new DictionaryTrie[26];
        this.word = false;
        this.character = 0;
    }

    public DictionaryTrie[] getChild() {
        return this.child;
    }

    public boolean getWord() {
        return this.word;
    }

    public char getCharacter() {
        return this.character;
    }

    public void setCharacter(char c) {
        this.character = c;
    }

    public void add(String s) {
        if (s.length() == 0) {
            this.word = true;
            return;

        }

        int index = s.charAt(0) - 65;

        if (this.child[index] == null) {
            this.child[index] = new DictionaryTrie();
            this.child[index].setCharacter(s.charAt(0));
        }

        this.child[index].add(s.substring(1));
    }

    public boolean isWord(String s) {
        if (s.length() == 0) {
            return this.getWord();
        }

        int index = s.charAt(0) - 65;
        if (this.child[index] == null) {
            return false;
        }

        return this.child[index].isWord(s.substring(1));
    }

}
