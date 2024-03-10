package Labs;

import java.util.Scanner;
public class Lab5{
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter a base you would to convert");
        int base = scan.nextInt();
        System.out.println("Enter a desired base to convert number");
        int baseTo = scan.nextInt();
        scan.nextLine();
        System.out.println("Please Enter number you would like to convert");
        String num = scan.nextLine();
        
        int decimalNumber = 0;
        int power = 1;
        if(2<base && baseTo<32 && 2<baseTo && base<32){
            
            // Convert the number from source base to decimal
            for (int i = num.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(num.charAt(i));
            decimalNumber += digit * power;
            power *= base;
            }

            // Convert the decimal number to target base
            StringBuilder targetNumber = new StringBuilder();
            while (decimalNumber > 0) {
                int digit = decimalNumber % baseTo;

                // Convert digit to character for bases > 10
                char digitChar = (digit < 10) ? (char) ('0' + digit) : (char) ('a' + (digit - 10));
                targetNumber.insert(0, digitChar);
                
                decimalNumber /= baseTo;
            }
            // Print the result
            System.out.println("The converted number is: " + targetNumber);
        }

        else{
            System.out.println("Please enter a base between 2 and 32");
        }
        scan.close();

        

        // // Convert the number from source base to decimal
        // int decimalNumber = Integer.parseInt(num, base);

        // // Convert the decimal number to target base
        // String targetNumber = Integer.toString(decimalNumber, baseTo);

        // // Print the result
        // System.out.println("The converted number is: " + targetNumber);
        
    }

    // public static String convertBase(String num, int baseFrom, int baseTo){
    //     for (int i = 0; i < num.length(); i++) {
    //         int digit = Character.getNumericValue(num.charAt(i));
    //         int targetDigit = digit % baseTo;
    //         targetBaseNumber.append(targetDigit);
    //         digit /= baseTo;
    //     }
    //     return targetBaseNumber.reverse().toString();
    // }
}
