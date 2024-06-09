public class Lab2RecursionLoan {
    public static void main(String[] args) {
        
        //Takes a loan that needs to be Paid Off
        // eg 250,000 and interest 3% rate annually
        // monthly loan repayment eg 1600
        //Calculate how long the long will take to pay off

        double loan = 250000;
        int payment = 1600; 
        double interestRate = 0.03; 
        double months = 0;
        
        // while(loan > 0){
        //     double interest = interestRate / 12; 
        //     loan +=  (interest*loan);
        //     loan -=   payment;   
        //     if(loan < 0){
        //         loan = 0;
        //     }
        //     System.out.println(loan);
        //     months ++;
        // }
        // System.out.println(months);

        System.out.println(calculate(loan,payment,interestRate,months));
    }
    public static double calculate(double loan, int payment, double interestRate, double months){
         
        if(loan <= 0){
            return months;
        }
        else{
            double interest  = interestRate / 12;
            loan +=  (interest*loan);
            loan -=   payment;           
            months++;
            System.out.println(loan);
            return calculate(loan,  payment, interestRate, months);
        }

    }
}
