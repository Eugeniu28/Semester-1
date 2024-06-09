package Lab5;

import java.util.Scanner;

public class Lab5 {
    public static void main(String[] args) {

        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(new Node(8));
        tree.insert(new Node(3));
        tree.insert(new Node(1));
        tree.insert(new Node(6));
        tree.insert(new Node(4));
        tree.insert(new Node(7));
        tree.insert(new Node(10));
        tree.insert(new Node(14));
        tree.insert(new Node(13));

        Scanner scan = new Scanner(System.in);
        // System.out.println("How many numbers you want to enter");
        // int num = scan.nextInt();

        // for(int i=0; i<num; i++){
        // tree.insert(new Node(scan.nextInt(i)));
        // }

        scan.close();

        tree.display();
        System.out.println("There are " + tree.countLevels() + " levels in the binary tree");

    }
}
