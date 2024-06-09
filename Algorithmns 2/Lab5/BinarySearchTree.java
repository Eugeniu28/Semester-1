package Lab5;

//Using a Binary Tree Search RECURSIVELY

public class BinarySearchTree {

    Node root;

    public void insert(Node node) { // method to insert our Nodes
        root = insertHelper(root, node); // using Recursion to pass in values
    }

    private Node insertHelper(Node root, Node node) {

        int data = node.data;

        if (root == null) { // This inputs our first Node of Binary Tree
            root = node;
            return root;
        } else if (data < root.data) { // Assign our values to left Node
            root.left = insertHelper(root.left, node);
        } else { // Assign our values to Right Node
            root.right = insertHelper(root.right, node);
        }

        return root; // Returning Our Current Node
    }

    public void display() {
        displayHelper(root);
    }

    private void displayHelper(Node root) {
        if (root != null) {
            displayHelper(root.left);
            System.out.println(root.data);
            displayHelper(root.right);
        }
    }

    public boolean search(int data) {
        return searchHelper(root, data);
    }

    private boolean searchHelper(Node root, int data) {
        if (root == null) { // Can't search for anything if our tree is empty
            return false;
        } else if (root.data == data) {
            return true; // We found what we're looking for
        } else if (root.data > data) { // this means we need to search the left side of tree
            return searchHelper(root.left, data);
        } else { // This means we go right of the tree
            return searchHelper(root.right, data);
        }
    }

    public void remove(int data) {

        if (search(data)) { // Checks even if that number is in our tree
            removeHelper(root, data);
        } else {
            System.out.println(data + " could not BE FOUND");
        }
    }

    private Node removeHelper(Node root, int data) {
        if (root == null) {
            return root;
        } else if (data < root.data) {
            root.left = removeHelper(root.left, data);
        } else if (data > root.data) {
            root.right = removeHelper(root.right, data);
        } else { // means we have found our node
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.right != null) { // find a successor to replace this node
                root.data = successor(root);
                root.right = removeHelper(root.right, root.data); // So we leave a child to that node
            } else { // find a predecessore to replace this node
                root.data = predecessor(root);
                root.left = removeHelper(root.left, root.data);
            }
        }
        return root;
    }

    private int successor(Node root) { // Finding the least value below the right child of this root node
        root = root.right;
        while (root.left != null) {
            root = root.left;
        }
        return root.data;
    }

    private int predecessor(Node root) { // Finding the least value below the left child of this root node
        root = root.left;
        while (root.right != null) {
            root = root.right;
        }
        return root.data;
    }

    public int countLevels() {
        return height(root) + 1;
    }

    private int height(Node root) {
        if (root == null) {
            return -1; // base case: if the tree is empty, height is -1
        } else {
            // Recursive call to calculate the height of the left and right subtrees
            int leftHeight = height(root.left);
            int rightHeight = height(root.right);

            // Return the greater height of the two subtrees and add one to account for the
            // root
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}
