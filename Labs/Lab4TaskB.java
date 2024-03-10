package Labs;

import java.util.Random;
public class Lab4TaskB{
    public static void main(String[] args) {
        int N = 1000000; 

        int withinCircle = 0;  
        
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            double x = random.nextDouble();  
            double y = random.nextDouble();  
            double distance = Math.sqrt(x * x + y * y);
            
            if (distance <= 1) {
                withinCircle++;
            }
        }
        
        double estimatedPi = 4.0 * withinCircle / N;
        System.out.println("Estimated value of π with " + N + " random points: " + estimatedPi);
    }
}

