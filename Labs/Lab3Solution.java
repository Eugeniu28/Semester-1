package Labs;

import java.util.*;
public class Lab3Solution{

 public static void main (String[] args){

 Scanner scan = new Scanner(System.in);
    int num1 = scan.nextInt();
    int num2 = scan.nextInt();
    int total=0;
 
    scan.close();

        for(int i=num1;i<=num2;i++){
        //check through the whole range and test each for divisibility
        boolean prime=true;
            for(int j=2;j<i;j++){
                if(i%j==0){
                    prime=false;
                }
            }
            if(prime==true){
                total++;
                //if it hasn't been divided by anything, it's a prime
            }
        }
            System.out.println(total);
    }
}
