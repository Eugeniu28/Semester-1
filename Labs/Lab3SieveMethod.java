package Labs;

import java.util.Scanner;
public class Lab3SieveMethod {
    public static void main(String args[]){

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter start of a range");
        int a = scan.nextInt();
        
        System.out.println("Please enter end of a range");
        int b = scan.nextInt();
        checkPrime(a,b);
        scan.close();
        
    }
    public static void checkPrime(int a, int b){

        boolean [] isPrime = new boolean [b+1];

        //Starts from 2 and sets the values in the array to true
        for(int i=2; i<=b; i++){
            isPrime[i] = true;
        }

        //sieve method
        for(int j=2; j * j <= b; j++){
            if(isPrime[j]==true){
                for (int i = j * j; i <= b; i += j){
                    isPrime[i] = false;
                }
            }
        }

        int count = 0;
        for(int i=a; i<=b; i++){
            if(isPrime[i]==true){
                count ++;
                //System.out.print(i+ " ");
            }
        }

        System.out.println();
        System.out.print("Total Prime Numbers in that range is "+ count);
    }
}
