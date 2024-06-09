import java.util.Scanner;
public class Lab3One {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //user input
        String word1 = "abcd";
        String word2 = "pq";
        scan.close();

        //converting to letters
        char [] a = word1.toCharArray(); 
        char [] b = word2.toCharArray();
        
        //Storing new char array and calling method
        char store [] = new char[a.length + b.length];
        merge(a, b, store);
        
        String result = String.valueOf(store);
        System.out.println(result);
       
    }

    public static void merge(char [] a, char [] b, char [] store){
        
        int i = 0, j = 0, k = 0;
        // Merges the array
        while (i < a.length && j < b.length) {
            store[k++] = a[i++];
            store[k++] = b[j++];
        }

        // Copies the remaining letters remaining in the char array of a 
        while (i < a.length) {
            store[k++] = a[i++];
        }

        // Copies the remaining letters remaining in the char array of b
        while (j < b.length) {
            store[k++] = b[j++];
        }
    }
}
