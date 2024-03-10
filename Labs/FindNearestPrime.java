package Labs;

import java.util.Scanner;
public class FindNearestPrime {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int x = scan.nextInt();

        // Calculate and print the distance to the nearest prime
        int distance = calculateNearestPrime(x);
        System.out.println("Distance to the nearest prime: " + distance);
        scan.close();
    }

    // Function to check if a number is prime
    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Function to calculate the distance to the nearest prime
    private static int calculateNearestPrime(int x) {
        
        if(isPrime(x)){
            return 0;
        }
        
        int countLeft = 0;
        int countRight = 0;

            for(int i=x; i>=2; i--){
                if(isPrime(i)){
                    countLeft = x - i;
                    break;
                }
            }

            for(int i = x; ; i++){
                if(isPrime(i)){
                    countRight = i-x;
                    break;
                }
            }

            return Math.min(countLeft, countRight);

    }
}
