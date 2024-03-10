package Labs;

import java.util.ArrayList;
import java.util.Scanner;
public class Lab1Complex {
    public static void main(String args[]){
        System.out.println("Choose option 1 or 2");
        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        if(a==1){
            checkPrime();
        }
        else{
            option();
        }
        scan.close();
    }
    //
    public static void checkPrime(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a Number");
        int a = 2;
        int b = scan.nextInt();

        boolean isPrime = true;
        while(a<b){
            if(b % a == 0) {
                isPrime = false;
                break;
            }
            a++;
        }
        if(isPrime) {
            System.out.println(b + " is a Prime Number");
        }
        else {
            System.out.println(b+ " is a NOT Prime Number");
        }
        scan.close();
    }
    public static void option(){
        ArrayList<Integer> c = new ArrayList<Integer>();
        int a = 10;
        int b = 30;

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
            System.out.print(c.get(j)+" ");
            }
           
        }
    }

}
