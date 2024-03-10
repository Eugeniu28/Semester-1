package Labs;

import java.util.ArrayList;
import java.util.Scanner;
public class Lab3slow {
    public static void main(String args[]){

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a range");
        int a = scan.nextInt();
        int b = scan.nextInt();
        printPrime(a,b);
        scan.close();

    }
    public static void printPrime(int a, int b){
       
        ArrayList<Integer> c = new ArrayList<Integer>();
        int countPrime = 0;

        //Adds a range of numbers in the array
        
        for(int i=a; i<=b; i++){
            c.add(i);
        }
        for(int j=0; j<c.size(); j++){  
            boolean check = true;  

            for(int i=2; i<c.get(j); i++){
                if(c.get(j) % i == 0){
                    check = false;
                    break;
                }
            }

            if(check == true){
            //System.out.print(c.get(j)+" ");
                countPrime++;
            }
            
        }
        System.out.println();
        System.out.println("Total is "+ countPrime);
    }
}
