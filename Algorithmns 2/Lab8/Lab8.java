import java.util.*;

public class Lab8 {

    static class Node implements Comparable<Node> { // Each node can be compared to each other

        char data; // Store the character in Node
        int frequency; // Occurence of Character
        Node left, right; // Represents the childs of the node

        public Node(char data, int frequency) { // Constructur for data and frequency
            this.data = data;
            this.frequency = frequency;
            left = null;
            right = null;
        }

        // Method to compare nodes based on their frequencies of nodes
        @Override
        public int compareTo(Node other) {
            return this.frequency - other.frequency; // Compare frequencies of nodes
        }
    }

    static class Tree { // This is binary tree with root node(parent)
        Node root;

        public Tree(Node root) { // Constructor
            this.root = root;
        }
    }

    static Map<Character, String> huffmanCodes = new HashMap<>();
    // HashMap stores values and the key, for each character

    public static void main(String[] args) {
        // User Input
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your sentence: ");
        String input = scan.nextLine();

        scan.close();

        // Part 1
        // Count Frequencies of each character in the input string
        Map<Character, Integer> frequencyMap = new HashMap<>();
        // Loop through each character in the input string
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c) || c == ' ') {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);// Update frequency of character
            }
        }

        System.out.println("Frequency of each character:");
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            System.out.println("'" + entry.getKey() + "' has a frequency of " + entry.getValue());

        }

        // Part 2 Create a priority queue and combine trees until only one is left
        PriorityQueue<Tree> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(tree -> tree.root.frequency));
        // Create a node for each character frequency and add it to the priority queue
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            Tree tree = new Tree(node);
            priorityQueue.add(tree);
        }
        // Combine nodes until only one node remains using (Huffman tree root)
        while (priorityQueue.size() > 1) {
            Tree tree1 = priorityQueue.poll();
            Tree tree2 = priorityQueue.poll();

            Node combinedRoot = new Node('\0', tree1.root.frequency + tree2.root.frequency);
            combinedRoot.left = tree1.root;
            combinedRoot.right = tree2.root;

            Tree combinedTree = new Tree(combinedRoot);
            priorityQueue.add(combinedTree); // Add combined node back to the priority queue
        }

        Tree huffmanTree = priorityQueue.poll(); // Build Huffman tree using character frequencies

        // Part 3
        // Generating Huffman codes using the Huffman tree
        generateHuffmanCodes(huffmanTree.root, "");

        // Prints the Huffman codes for each character
        System.out.println("\nHuffman Codes:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
        }
    }

    // method to generate Huffman codes recursively
    static void generateHuffmanCodes(Node node, String code) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.data, code);
        }
        generateHuffmanCodes(node.left, code + "0");
        generateHuffmanCodes(node.right, code + "1");
    }
}