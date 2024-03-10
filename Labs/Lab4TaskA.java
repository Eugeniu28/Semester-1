package Labs;

import java.util.Random;
public class Lab4TaskA {
    public static void main(String[] args) {
        int simulations = 10000;

        double e = monteCarloESimulation(simulations);
        double trueValue = 2.71828;
        double c = Math.abs(e - trueValue);

        System.out.println("Estimated value of 'e' with " + simulations + " steps: " + e);
        System.out.println("Absolute difference is " + c);
    }

    public static double monteCarloESimulation(int n) {
        Random r = new Random();
        double e = 0.0;

        for (int i = 0; i < n; i++) {
            double total = 0.0;
            int count = 0;

            while (total < 1) {
                double x = r.nextDouble(); // Random numbers between 0 and 1
                total += x;
                count++;
            }

            e += count;
        }

        e /= n; // Calculate the average count
        return e;
    }
}
