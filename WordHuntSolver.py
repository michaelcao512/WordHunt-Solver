from random import randint

WORD_LIST_LOCATION = "Collins Scrabble Words (2019).txt"
class TrieNode:
    def __init__(self, char: str):
        self.char = char
        self.word = False
        self.children = {}
class DictionaryTrie:
    def __init__(self):
        self.root = TrieNode("")
        self.num_words = 0

    def add(self, str: str):
        curr = self.root
        for c in str:
            if (c in curr.children):
                curr = curr.children[c]
            else:
                newLetter = TrieNode(c)
                curr.children[c] = newLetter
                curr = newLetter
        curr.word = True
        self.num_words += 1
    def isWord(self, str: str):
        curr = self.root
        isWord = True
        for c in str:
            if (c in curr.children):
                curr = curr.children[c]
            else:
                isWord = False
        if(isWord and curr.word):
            return True
        return False
    def initiateDictionary(self):
        with open(WORD_LIST_LOCATION) as f:
            for word in f:
                word = word.rstrip('\n')
                if(len(word) >= 3):
                    self.add(word) 
        f.close()

WORDS_LIST = DictionaryTrie()
WORDS_LIST.initiateDictionary()


BOARD_HEIGHT = 4
BOARD_WIDTH = 4
class GameBoard:
    def __init__(self, letters: str):
        if (len(letters) != BOARD_HEIGHT * BOARD_WIDTH):
            return
        self.board = [ [None for c in range(BOARD_WIDTH)] for r in range(BOARD_HEIGHT)]
        for r in range(BOARD_HEIGHT):
            for c in range(BOARD_WIDTH):
                n = r*BOARD_HEIGHT + (c)%BOARD_WIDTH
                self.board[r][c] = (letters[n]).upper()
        
        self.listWords = set()
    

    def getAdjacentTile(r,c):
        l = [(r-1,c-1), (r-1,c), (r-1,c+1), (r,c+1), 
        (r+1,c+1), (r+1,c), (r+1,c-1), (r,c-1)]
        return l

    def sb(self, r: int, c: int, visited: bool, d: TrieNode, word: str):
        if(r < 0 or c < 0 or r >= BOARD_HEIGHT or c >= BOARD_WIDTH):
            return
        if(visited[r][c]):
            return

        letter = self.board[r][c]
        visited[r][c] = True

        if(letter in d.children):
            word += letter
            if (WORDS_LIST.isWord(word)):
                self.listWords.add(word)
            
            for rr, cc in GameBoard.getAdjacentTile(r,c):
                self.sb(rr, cc, visited, d.children[letter], word)
        visited[r][c] = False

    def searchBoard(self):
        for r in range(BOARD_HEIGHT):
            for c in range(BOARD_WIDTH):
                self.sb(r, c, [[False for c in range(BOARD_WIDTH)] for r in range(BOARD_HEIGHT)], WORDS_LIST.root, "")

    def printBoard(self):
        for r in range(BOARD_HEIGHT):
            for c in range(BOARD_WIDTH):
                print(self.board[r][c], end=' ')
            print()
    def printWordList(self):
        for x in self.listWords:
            print(x)


def generateRandomBoard():
    l = ['']*(BOARD_HEIGHT*BOARD_WIDTH)
    for i in range(BOARD_HEIGHT*BOARD_WIDTH):
        r = chr (randint(ord('A'), ord('Z')))
        l[i] = r
    g = GameBoard(l)
    return g
def generateBoard(str):
    if len(str) != BOARD_HEIGHT * BOARD_WIDTH:
        return
    l = []
    for c in str:
        l.append(c)
    g = GameBoard(l)
    return g

g = generateRandomBoard()

g.searchBoard()
g.printBoard()
g.printWordList()