import java.util.Arrays;

public class Occurrence {
    private char[] chars;
    private int wordLength;
    private int occurrences;

    public Occurrence() {
    }


    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public char[] getChars() {
        return chars;
    }

    public int getWordLength() {
        return wordLength;
    }

    @Override
    public String toString() {
        return "{ " + Arrays.toString(chars) + " , " + wordLength + " }" ;
    }
}
