import java.io.*;
import java.util.Scanner;

public class Lab9test {

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

            // Run Dijkstra's Algorithm
            int[] shortestDistances = dijkstra(distances, 0);
            // Calculate Total Distance
            int totalDistance = 0;
            for (int dist : shortestDistances) {
                totalDistance += dist;
            }
            // Output Result
            System.out.println("Total distance traveled: " + totalDistance + " meters.");

        } catch (Exception e) {
            System.err.println(e);
        }

        // Prints the file deliveries, addresses and their distances.
        // for (int i = 0; i < 100; i++) {
        // System.out.println(addresses[i]);
        // }

        // for (int i = 0; i < 100; i++) {
        // for (int j = 0; j < 99; j++) {
        // System.out.print(distances[i][j] + ",");
        // }
        // System.out.println(distances[i][99]);
        // }
    }

    // Dijkstra's Algorithm
    private static int[] dijkstra(int[][] graph, int source) {
        int[] dist = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[source] = 0;

        for (int count = 0; count < graph.length - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;
            for (int v = 0; v < graph.length; v++) {
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }
        return dist;
    }

    private static int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}
