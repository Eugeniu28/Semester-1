import java.io.*;
import java.util.*;

public class Lab10Working {

    public static void main(String[] args) {

        // Scan our File Deliveries
        File file = new File(
                "C:\\Users\\eugen\\Downloads\\Semester 2\\CS211 Algorithms 2\\Labs\\Lab10\\deliveries.csv");

        String[] addresses = new String[100];
        int[][] distances = new int[100][100];

        try {
            Scanner scan = new Scanner(file);
            for (int i = 0; i < 100; i++) {
                String line = scan.nextLine();
                String[] parts = line.split(","); // Split the line by commas
                addresses[i] = parts[0]; // get the address
                for (int j = 0; j < 100; j++) { // get the distances
                    distances[i][j] = Integer.parseInt(parts[j + 1].trim());
                }
            }
            scan.close();

        } catch (Exception e) {
            System.err.println(e);
        }

        List<Integer> optimalPath = findOptimalPath(distances);

        System.out.println("Optimal path to visit every house:");

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

    // Find optimal path using Dijkstra's algorithm
    private static List<Integer> findOptimalPath(int[][] distances) {
        int n = distances.length;
        boolean[] visited = new boolean[n];
        List<Integer> path = new ArrayList<>();
        // int totalDistance = 0;

        // Start from Apache Pizza (house 0)
        path.add(0);
        visited[0] = true;

        for (int i = 1; i < n; i++) {
            int currentHouse = path.get(path.size() - 1);
            int nearestHouse = findNearestUnvisitedHouse(currentHouse, visited, distances);
            path.add(nearestHouse);
            visited[nearestHouse] = true;
            // totalDistance += distances[currentHouse][nearestHouse];
        }
        path.add(0);

        return path;
    }

    // Find nearest unvisited house from the current house
    private static int findNearestUnvisitedHouse(int currentHouse, boolean[] visited, int[][] distances) {
        int nearestHouse = -1;
        int shortestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i] && distances[currentHouse][i] < shortestDistance) {
                nearestHouse = i;
                shortestDistance = distances[currentHouse][i];
            }
        }
        return nearestHouse;
    }
}