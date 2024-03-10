package Labs;

import java.util.*;
public class Lab6{
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = scan.next();
        }
        
        sortWords(words);

        // Print out sorted words
        for (String word : words){
           System.out.println(word);
           System.out.println(Score(word));
        }

        scan.close();
    }

    public static void sortWords(String[] words) {
        
        //Bubble Sort
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = 0; j < words.length - 1 - i; j++) {
                
                if (Score(words[j]) > Score(words[j + 1])) {    
                    String temp = words[j];
                    words[j] = words[j + 1];
                    words[j + 1] = temp;
                }
            }
        }
        //Sort the words Alphabetically
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].compareTo(words[j]) > 0) {
                    String temp = words[i];
                    words[i] = words[j];
                    words[j] = temp;
                }
            }
        }
        
    }
    private static int Score(String word) {
        int score = 0;
        for (char c : word.toLowerCase().toCharArray()) {
            switch (c) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                case 'l':
                case 'n':
                case 's':
                case 't':
                case 'r':
                    score += 1;
                    break;
                case 'd':
                case 'g':
                    score += 2;
                    break;
                case 'b':
                case 'c':
                case 'm':
                case 'p':
                    score += 3;
                    break;
                case 'f':
                case 'h':
                case 'v':
                case 'w':
                case 'y':
                    score += 4;
                    break;
                case 'k':
                    score += 5;
                    break;
                case 'j':
                case 'x':
                    score += 8;
                    break;
                case 'q':
                case 'z':
                    score += 10;
                    break;
            }
        }
        return score;
    }
}
