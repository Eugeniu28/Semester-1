package Lab7EngSpan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lab7 {

    public static void main(String[] args) {

        BinaryTree tree = new BinaryTree();

        String translationFile = "C:/Users/eugen/OneDrive/Desktop/Semester 2/CS211 Algorithms 2/Labs/Lab7EngSpan/EnglishSpanish.csv";
        String frequencyFile = "C:/Users/eugen/Downloads/Semester 2/CS211 Algorithms 2/Labs/Lab7EngSpan/EnglishFrequencies.csv";
        String line;
        String csvSplitBy = ",";

        Map<String, String> translations = new HashMap<>();
        Map<String, Long> wordFrequencies = new HashMap<>();

        // Read translations from CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(translationFile))) {
            while ((line = br.readLine()) != null) {
                String[] translation = line.split(csvSplitBy);
                translations.put(translation[0], translation[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read word frequencies from CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(frequencyFile))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String word = parts[0];
                    long frequency = Long.parseLong(parts[1].trim());
                    wordFrequencies.put(word, frequency);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Insert translations into the binary tree, considering frequency from the
        // frequency file
        for (String word : translations.keySet()) {
            long frequency = wordFrequencies.getOrDefault(word, 0L);
            tree.insert(word, translations.get(word), frequency);
        }

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter English words (type 'exit' to finish):");

        StringBuilder spanishText = new StringBuilder();
        int totalSteps = 0;
        int totalWords = 0;

        String englishWord;
        while (true) {
            System.out.print("Enter an English word: ");
            englishWord = scan.nextLine().trim();

            if (englishWord.equalsIgnoreCase("exit")) {
                break; // Exit the loop if user inputs 'exit'
            }

            String translatedWord = tree.translate(englishWord.toLowerCase());
            if (translatedWord != null) {
                totalSteps += tree.getTranslationSteps(englishWord.toLowerCase());
                totalWords++;
                System.out.println("Spanish translation: " + translatedWord); // Print the translation
                spanishText.append(translatedWord).append(" ");
            } else {
                System.out.println("Word not found in dictionary: " + englishWord); // Print error message
                spanishText.append("[").append(englishWord).append("] ");
            }
        }

        System.out.println("Spanish translation: " + spanishText.toString().trim());
        System.out.println("Total words translated: " + totalWords);
        System.out.println("Total steps taken: " + totalSteps);

        // Calculate average steps per word
        if (totalWords > 0) {
            double averageStepsPerWord = totalSteps / (double) totalWords;
            System.out.println("Average steps per word: " + averageStepsPerWord);
        } else {
            System.out.println("No words were translated.");
        }
        scan.close();
    }
}
