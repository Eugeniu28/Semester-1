package Labs;


public class Lab4Solution{

 public static void main (String[] args){
    int simulations = 1000000;

    double totalcount = 0;

    for(int i = 0; i < simulations; i++){
 //surprisingly, e is the average number of randoms you need to add to get 1
        double total = 0;
        
        while(total<1){
            totalcount++;
            total=total+Math.random();
        }
    }
 System.out.println(totalcount/simulations);
 }
}