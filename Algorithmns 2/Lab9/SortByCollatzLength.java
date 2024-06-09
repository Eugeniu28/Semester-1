import java.util.Scanner;

public class SortByCollatzLength {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the value of n: ");
        int n = scanner.nextInt();
        scanner.close();

        // An arry to store the Numbers
        CollatzNumber[] numbers = new CollatzNumber[n];

        // Calculate Collatz lengths for numbers from 1 to n
        for (int i = 0; i < n; i++) {
            int length = calculateCollatzLength(i + 1);
            // Create a CollatzNumber object for each number and its Collatz length
            numbers[i] = new CollatzNumber(i + 1, length);
        }

        // Sort the numbers based on their Collatz lengths
        quickSort(numbers, 0, n - 1);

        // Find the position of the input number in the sorted sequence
        int position = 0;
        for (int i = 0; i < n; i++) {
            if (numbers[i].number == n) {
                position = i + 1;
                break;
            }
        }

        // Output the position in Collatz
        System.out.println("The position of " + n + " in sorterd Collatz sequence is: " + position);

        // Output the Collatz length of the input n
        int collatzLengthOfN = calculateCollatzLength(
                n);
        System.out.println("The collatz length of " + n + " is " + collatzLengthOfN);

        // Output the number with the highest Collatz length
        System.out.println("Between 1 and " + n + " the number with highest Collatz Length: " + numbers[n - 1].number
                + " with a length of " + numbers[n - 1].length);
        System.out.println("Position 1 number " + getPosition(numbers, 1));
        System.out.println("Position 10 number " + getPosition(numbers, 10));
        System.out.println("Position 100th number " + getPosition(numbers, 100));
        System.out.println("Position 1000th number " + getPosition(numbers, 1000));
    }

    // Quicksort algorithm to sort numbers based on their Collatz lengths
    public static void quickSort(CollatzNumber[] numbers, int low, int high) {
        if (low < high) {
            // Partition the array and recursively sort each part
            int pi = partition(numbers, low, high);
            quickSort(numbers, low, pi - 1);
            quickSort(numbers, pi + 1, high);
        }
    }

    // Partitioning function for quicksort
    public static int partition(CollatzNumber[] numbers, int low, int high) {
        int pivot = numbers[high].length;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (numbers[j].length <= pivot) {
                // Swap numbers if the Collatz length is less than or equal to the pivot
                i++;
                CollatzNumber temp = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = temp;
            }
        }
        // Swap the pivot number to its correct position
        CollatzNumber temp = numbers[i + 1];
        numbers[i + 1] = numbers[high];
        numbers[high] = temp;
        return i + 1;
    }

    // Function to calculate the Collatz length of a number
    public static int calculateCollatzLength(int n) {
        int length = 0;
        while (n != 1) {
            // Loop the Collatz sequence until reaching 1
            if (n % 2 == 0) {
                n /= 2;
            } else {
                n = 3 * n + 1;
            }
            length++;
        }
        return length + 1; // Add 1 to include the initial number itself
    }

    public static int getPosition(CollatzNumber[] numbers, int num) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i].number == num) {
                return i + 1; // Return the position of the number in the sorted sequence
            }
        }
        return 0; // Not found
    }

}