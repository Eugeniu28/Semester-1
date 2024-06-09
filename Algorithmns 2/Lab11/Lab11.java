import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Lab11 {
    public static void main(String[] args) {

        // Scan our csv file store names and vertex
        File file = new File("C:\\Users\\eugen\\Downloads\\Semester 2\\CS211 Algorithms 2\\Labs\\Lab11\\Graph.csv");
        String[] addresses = new String[10];
        int[][] distances = new int[10][10];

        //
        try {
            Scanner scan = new Scanner(file);
            for (int i = 0; i < 10; i++) {
                String line = scan.nextLine();
                String[] parts = line.split(","); // Split the line by commas
                addresses[i] = parts[0]; // get the address
                for (int j = 0; j < 10; j++) { // get the distances
                    distances[i][j] = Integer.parseInt(parts[j + 1].trim());
                }
            }
            scan.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        // Take User Input
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input starting vertex: ");
        String startVertex = scan.next();
        scan.close();

        // Find the index of the starting vertex in the addresses array
        int startIndex = -1; // Initialize with a value that indicates vertex not found
        for (int i = 0; i < 10; i++) {
            if (addresses[i].equals(startVertex)) { // Check if vertex name matches
                startIndex = i; // set the index of the starting vertex
                break;
            }
        }

        // If our starting vertex is not found, print an error message and exit program
        if (startIndex == -1) {
            System.out.println("Vertex not found!");
            return;
        }

        // Using Breadth-first search to find the shortest path to each vertex from our
        // Starting vertex
        int[] steps = new int[10]; // Stores steps to reach each vertex from starting one
        boolean[] visited = new boolean[10]; // Track visited vertices so we avoid visiting them again
        Queue<Integer> queue = new LinkedList<>(); // Using Queue to stores vertices based on Breadth-first search
        queue.add(startIndex); // Add the starting vertex
        visited[startIndex] = true; // Keep track that start vertex has been visited

        // Continue until it reaches all vertices
        while (!queue.isEmpty()) {
            int current = queue.poll(); // return current vertex from the queue
            for (int i = 0; i < 10; i++) { // Loop through all vertices to find neigbors of vertex
                if (distances[current][i] == 1 && !visited[i]) {
                    steps[i] = steps[current] + 1; // update number of steps to reach vertex 1
                    queue.add(i); // add vertex i to queue to explore neighbors
                    visited[i] = true; // mark vertex visited
                }
            }
        }

        // Print the number of steps to reach each vertex from start vertex
        for (int i = 0; i < 10; i++) {
            if (steps[i] == 0 && i != startIndex) {
                System.out.println(addresses[i] + ": -1"); // If it's unreachable
            } else {
                System.out.println(addresses[i] + ": " + steps[i]); // Prints steps to reach the vertex
            }
        }
    }
}
