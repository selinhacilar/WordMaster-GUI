import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WordList {
    private ArrayList<String> allWords;
    private final String WORDFILE = "resources/1000EnglishWords";
    private String secretWord;
    private char[] secretWordArray;
    private char[] displayWordArray;
    public final char SINGLE_CHAR = '_';
    public final char MATCHING_CHAR = '*';
    private int maxWordLength;


    /**
     * Reads all of the words from the file 1000EnglishWords.txt
     * Shuffles the list and then orders the shuffled list by list size
     * Finds the maximum length of the words in the list
     */
    public WordList() {
        allWords = FileIO.readDataFromFile(WORDFILE);
        // Shuffling the list
        Collections.shuffle(allWords);
        //Sort the shuffled list by length
        Collections.sort(allWords, Comparator.comparing(String::length));
        maxWordLength = allWords.get(allWords.size() - 1).length();
    }

    /**
     * Returns the maximum length of the words in the list
     * @return
     */
    public int getMaxWordLength() {
        return maxWordLength;
    }

    /**
     * Sets the Array used to display the word to empty
     */
    public void resetDisplayWordArray() {
        for (int i = 0; i < secretWord.length(); i++) {
            displayWordArray[i] = SINGLE_CHAR;
        }
    }

    /**
     * Sets the secret random word
     * @param wordLength
     */
    public void setSecretWord(int wordLength) {
        secretWord = getRandomWord(wordLength);
        secretWordArray = secretWord.toCharArray();
        displayWordArray = new char[secretWord.length()];
        resetDisplayWordArray();
    }

    /**
     * Returns the secret word - used for debugging
     * @return
     */
    public String getSecretWord() {
        return secretWord;
    }

    /**
     * Prints the current word Array
     * @return
     */
    public void printDisplayWordArray() {
        for (char c : displayWordArray) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /* We create a method which we will use on the Game class when we want to display the word array according to
    the length of the word player chooses. We first create a StringBuilder mySecret. If the displayWordArray
    is not equal to null and the length of it is greater than 0, we will go through the displayWordArray and append
    the underscores. Then we return the displayWordArray by changing it to String from char by using String.valueOf()
     */
    public String getDisplayWordArray() {
        StringBuilder mySecret = new StringBuilder();
        if (displayWordArray != null && displayWordArray.length > 0)
        {   for(int i=0; i < this.displayWordArray.length; i++) {
            mySecret.append(displayWordArray[i]);
            mySecret.append(" ");
        }
            return String.valueOf(displayWordArray);
        }
        else return "";
    }

    /**
     * Used to compare the word the user has guessed with the current secret word
     * @param userWord
     * @return true if the words match
     */
    public boolean compareWords(String userWord) {
        char[] userWordArray = userWord.toCharArray();
        String lettersFound = "";
        resetDisplayWordArray();
        for (int i = 0; i < secretWordArray.length; i++) {
            if (secretWordArray[i] == userWordArray[i]) {
                displayWordArray[i] = userWordArray[i];
                lettersFound += userWordArray[i];
            }
        }
        if (lettersFound.equals(secretWord)) {
            return true;
        }
        for (int i = 0; i < secretWordArray.length; i++) {
            String subStr = Character.toString(userWordArray[i]);
            if (!lettersFound.contains(subStr) && secretWord.contains(subStr)) {
                System.out.println(MATCHING_CHAR);
                displayWordArray[i] = MATCHING_CHAR;
            }
        }
        return false;
    }

    /**
     * Gets a random word from the word list
     * @param length
     * @return
     */
    public String getRandomWord(int length) {
        if(length <=0)
            return "";
        ArrayList<String> currentWords = new ArrayList<>();
        for (String str: allWords) {
            if (str.length() == length) {
                currentWords.add(str);
            }
        }
        // Shuffling the list
        Collections.shuffle(currentWords);
        return currentWords.get(0);
    }
}
