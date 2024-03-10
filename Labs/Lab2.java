package Labs;

import java.util.Scanner;
public class Lab2{
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        //String a = scan.nextLine();

        String a = "45396829985824395";
        if(4>=a.length() && a.length()<=30){
            checkCard(a);
        }
        else{
            System.out.println("Invalid Card number");
        }
        scan.close();
    }
    public static void checkCard(String a){
        int sum =0;
        //Start from 
        for(int i=a.length()-1; i>=0; i-=2){
            sum += Character.getNumericValue(a.charAt(i));
            //System.out.print(sum +" ");
        }
        //System.out.println(sum);
        int sum2 = 0; 
        int result = 0; 
        for(int i=a.length()-2; i>=0; i-=2){
            
            sum2 = Character.getNumericValue(a.charAt(i)) * 2;
            if(sum2>9){
                sum2 = sum2 - 9;
            }
           result += sum2;
        }//System.out.println(result);

        //Check the sum and validity
        if((result+sum) % 10 == 0){
            System.out.println(a+" Credit Card is Valid");
        }
        else{
            System.out.println(a+" Credit Card is InValid");
        }
    }
}