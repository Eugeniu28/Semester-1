package Labs;

public class Lab4bSolution {
    public static void main (String[] args){
        int simulations = 1000000;
       
        double countoutsidecircle = 0;
        double countinsidecircle = 0;
       
        for(int i = 0; i < simulations; i++){   
            double randomx = Math.random();
            double randomy = Math.random();
        //pick a random point and figure out if it's in the circle or outside
        //use Pythagoras theorem
            if(Math.sqrt(Math.pow(Math.abs(0.5-
        randomx),2)+Math.pow(Math.abs(0.5-randomy),2))<0.5){
            countinsidecircle++;
            }else{
            countoutsidecircle++;
            }
        }
        
        
        System.out.println(countinsidecircle);
        System.out.println(countoutsidecircle);
        //area of a circle is PIr^2 and the radius is 0.5, so multiply the ratio by 4
        //the whole area is of size 1, so the ratio between them is PI/4
       
        System.out.println(countinsidecircle*4/simulations);
}
}
