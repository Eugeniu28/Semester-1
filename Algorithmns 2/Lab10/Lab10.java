import java.io.*;
import java.util.*;

public class Lab9 {

    public static void main(String[] args) {

        // Scan our File
        File file = new File(
                "C:\\Users\\eugen\\Downloads\\Semester 2\\CS211 Algorithms 2\\Labs\\Lab10\\deliveries.csv");

        String[] addresses = new String[100];
        int[][] distances = new int[100][100];

        try {
            Scanner scan = new Scanner(file);
            for (int i = 0; i < 100 && scan.hasNextLine(); i++) {
                String line = scan.nextLine();
                String[] parts = line.split(",");
                addresses[i] = parts[0];
                for (int j = 0; j < 100; j++) {
                    distances[i][j] = Integer.parseInt(parts[j + 1].trim());
                }
            }
            scan.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        // Stores the optimalPath
        List<Integer> optimalPath = findOptimalPath(distances);

        System.out.println("*");
        System.out.println("Optimal path to visit every house:");
        System.out.println("*");

        // Prints the houses optimal path and distance
        int totalDistance = 0;
        int currentHouse = optimalPath.get(0); // Apache Pizza
        for (int i = 1; i < optimalPath.size(); i++) {
            int nextHouse = optimalPath.get(i);
            System.out.println("FROM: " + addresses[currentHouse] + "\nTO: " + addresses[nextHouse] + "\nDistance: "
                    + distances[currentHouse][nextHouse]);
            System.out.println("*");
            totalDistance += distances[currentHouse][nextHouse];
            currentHouse = nextHouse;
        }
        System.out.println("Total distance travelled: " + totalDistance);
    }

    // Find optimal path using 2-opt algorithm
    private static List<Integer> findOptimalPath(int[][] distances) {
        int n = distances.length;
        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            path.add(i);
        }
        path.add(0); // Return to the start point to make it a cycle

        // Finds the path and re-orders the routes
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i = 1; i < n - 1; i++) {
                for (int k = i + 1; k < n; k++) {
                    int delta = -distances[path.get(i - 1)][path.get(i)]
                            - distances[path.get(k)][path.get(k + 1)]
                            + distances[path.get(i - 1)][path.get(k)]
                            + distances[path.get(i)][path.get(k + 1)];
                    if (delta < 0) { // if this change shortens the route
                        Collections.reverse(path.subList(i, k + 1));
                        improvement = true;
                    }
                }
            }
        }
        return path;
    }
}