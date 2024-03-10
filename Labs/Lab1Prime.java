package Labs;

import java.util.*;
public class Lab1Prime{
    public static void main(String args[]){

        Scanner scan = new Scanner(System.in);

        System.out.println("Choose option 1 or 2");
        int num = 2;
        
        if(num==1){
            System.out.println("Please enter a number");
            int a = 13;
            System.out.println(a + " is Prime Number " + checkPrime(a));
        }
        else if(num==2){
            System.out.println("Enter Start of Range");
            int y = 10;
            System.out.println("Enter End of Range");
            int z = 30;
            checkRange(y,z);
        }
        else{
            System.out.println("Invalid Options Entered");
        }
        scan.close();
    }
    public static boolean checkPrime(int a){
        int x = 2; 
        boolean isPrime = true;
        while (x<a){
            if(a % x == 0){
                isPrime = false;
                break;
            }
            x++;
        }

        // for(int i=2; i<a; i++){
        //     if(a % i == 0){
        //         isPrime = false;
        //         break;
        //     }
        // }
        return isPrime;

        
    }
    public static void checkRange(int y, int z){
        ArrayList <Integer> abc = new ArrayList<>();

        for(int i = y; i<=z; i++){
                if(checkPrime(i)){
                    abc.add(i);
                }
            }

        System.out.println("Prime numbers in the range " + y + " to " + z + ": " + abc);
        
        // for(int b : abc){
        //     System.out.println("Prime numbers in the range "+y+" "+z+" "+b);
        // }
    }
}
