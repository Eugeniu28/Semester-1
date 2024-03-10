import java.util.*;
public class Lab11{

 public static void main (String[] args){

    Scanner scan = new Scanner(System.in);
    int n = scan.nextInt();

    scan.close();
 //you are in nth place - will you survive?
    double simulations = 1000000;

    double successes = 0;

        for(int i = 0; i < simulations; i++){
            int currentplayer = 1;
            int wherestanding = 0;
            int squaresdiscovered = 0;
                for(int j=1;j<=n;j++){ //simulate all players up to you
                    boolean dead=false;
                    while(!dead&&wherestanding<17){
                            if(wherestanding>=squaresdiscovered){

                            if(Math.random()<0.5){
                                dead=true; //chose wrong square and died
                            }
                            squaresdiscovered++;
                            }
                            if(Math.random()<0.01){
                                dead=true; //died jumping
                            }
                            if(dead){
                                wherestanding=0; //start at the beginning
                            }else{
                                wherestanding++; //move up one space
                            }
                    }
                currentplayer++; //next player's turn!
            }
        if(wherestanding==17){
            successes++;
        }
    }

    System.out.println(Math.round(successes*10000.0/simulations)/100.0+"%");
    }
}