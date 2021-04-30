import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FrequencyManager {
    private String givenCharacters = "";
    private String givenWord = "LOGIC";

    public void countFrequency() {
        char[] chars = prepareKeyChars(chooseKeyWord());

        System.out.println("Podaj zdanie do sprawdzenia: ");
        Scanner sc = new Scanner(System.in);
        String[] words = prepareSentence(sc.nextLine());

        List<Occurrence> list = new ArrayList<>();
        int totalNumberOfOccurences = 0;
        int totalNumberOfLetters = 0;

        for (String word : words) {
            totalNumberOfLetters += word.length();
        }

        for (int i = 0; i < words.length; i++) {
            int[] occurrencesOfLetters = new int[chars.length];

            for (int j = 0; j < chars.length; j++) {
                occurrencesOfLetters[j] = StringUtils.countOccurrencesOf(words[i], String.valueOf(chars[j]));
            }

            int occurrencesSum = IntStream.of(occurrencesOfLetters).sum();
            if (occurrencesSum > 0) {
                totalNumberOfOccurences += occurrencesSum;

                char[] tempArray = new char[chars.length];
                int z = 0;
                for (int j = 0; j < chars.length; j++) {
                    if (occurrencesOfLetters[j] > 0) {
                        tempArray[z] = chars[j];
                        z++;
                    }
                }
                char[] existingCharacters = new char[z];
                System.arraycopy(tempArray, 0, existingCharacters, 0, z);

                Occurrence wordRepresentation = new Occurrence();
                wordRepresentation.setChars(existingCharacters);
                wordRepresentation.setWordLength(words[i].length());
                wordRepresentation.setOccurrences(occurrencesSum);

                if (list.isEmpty()) {
                    list.add(wordRepresentation);
                }
                for (int j = 0; j < list.size(); j++) {
                    if (i == 0) break;
                    boolean x = Arrays.equals(list.get(j).getChars(), wordRepresentation.getChars());
                    boolean y = list.get(j).getWordLength() == wordRepresentation.getWordLength();
                    if (x && y) {
                        int newOccurrencesValue = list.get(j).getOccurrences() + wordRepresentation.getOccurrences();
                        list.get(j).setOccurrences(newOccurrencesValue);
                        break;
                    } else if (j == (list.size() - 1)) {
                        list.add(wordRepresentation);
                        break;
                    }
                }
            }
        }
        printResult(list, totalNumberOfLetters, totalNumberOfOccurences);
    }

    private void printResult(List<Occurrence> list, int charSum, int totalOccurrences) {
        list = list.stream()
                .sorted(Comparator.comparingInt(Occurrence::getOccurrences))
                .collect(Collectors.toList());

        for (Occurrence listEl : list) {
            double percentage = (double) listEl.getOccurrences() / (double) totalOccurrences;
            percentage = Math.round(percentage * 100.0) / 100.0;
            System.out.println(listEl.toString() + " = "
                    + percentage + " ( " + listEl.getOccurrences() + "/" + totalOccurrences + " )");
        }
        double totalFrequency = Math.round(((double) totalOccurrences / (double) charSum) * 100.0) / 100.0;
        System.out.println("TOTAL FREQUENCY: " + totalFrequency + " (" + totalOccurrences + "/" + charSum + ")");
    }

    private char[] prepareKeyChars(String characters) {
        characters = characters.replaceAll("[^A-Za-z]", "").toUpperCase();
        char[] initialArray = characters.toCharArray();
        Arrays.sort(initialArray);
        int length = initialArray.length;
        char[] newArray = new char[length];
        int x = 0;
        for (int i = 0; i < length - 1; i++) {
            if (initialArray[i] != initialArray[i + 1]) {
                newArray[x] = initialArray[i];
                x++;
            }
        }
        if (initialArray[length - 2] != initialArray[length - 1])
            newArray[x] = initialArray[length - 1];

        char[] finalArray = new char[x + 1];
        System.arraycopy(newArray, 0, finalArray, 0, x + 1);

        return finalArray;
    }

    private String[] prepareSentence(String word) {
        word = word.replaceAll("[^a-zA-Z0-9 ]", " ").toUpperCase();
        String[] words = word.split(" ");
        Arrays.sort(words, Comparator.comparingInt(String::length));

        return words;
    }

    private String chooseKeyWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Słowo klucz to: " + givenWord + ", jeśli chcesz je zmienić wpisz 'y' lub 'n' aby kontunuować.");
        boolean z = true;
        while (z) {
            String nextLine = sc.nextLine();
            if (nextLine.equals("y")) {
                System.out.println("Podaj nowe słowo: ");
                givenCharacters = sc.nextLine();
                z = false;
            } else if (nextLine.equals("n")) {
                givenCharacters = givenWord;
                z = false;
            } else System.err.println("Wprowadź poprawny znak.");
        }

        return givenCharacters;
    }
}
