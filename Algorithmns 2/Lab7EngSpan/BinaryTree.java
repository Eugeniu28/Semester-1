package Lab7EngSpan;

class BinaryTree {
    private TreeNode root;
    private int totalSteps; // Total steps taken during translations

    public BinaryTree() {
        root = null;
        totalSteps = 0;
    }

    public void insert(String englishWord, String spanishWord, long frequency) {
        root = insertRec(root, englishWord, spanishWord, frequency);
    }

    private TreeNode insertRec(TreeNode root, String englishWord, String spanishWord, long frequency) {
        if (root == null) {
            root = new TreeNode(englishWord, spanishWord, frequency);
            return root;
        }
        int comparison = englishWord.compareTo(root.engWord);
        if (comparison < 0)
            root.left = insertRec(root.left, englishWord, spanishWord, frequency);
        else if (comparison > 0)
            root.right = insertRec(root.right, englishWord, spanishWord, frequency);
        return root;
    }

    public String translate(String englishWord) {
        return translateRec(root, englishWord);
    }

    private String translateRec(TreeNode root, String englishWord) {
        if (root == null)
            return null;
        int comparison = englishWord.compareTo(root.engWord);
        if (comparison == 0)
            return root.spanWord;
        else if (comparison < 0) {
            totalSteps++; // Increment total steps
            return translateRec(root.left, englishWord);
        } else {
            totalSteps++; // Increment total steps
            return translateRec(root.right, englishWord);
        }
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(TreeNode root) {
        if (root == null)
            return 0;
        else {
            int leftHeight = heightRec(root.left);
            int rightHeight = heightRec(root.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    public int getTranslationSteps(String englishWord) {
        totalSteps = 0; // Reset total steps before each translation
        translate(englishWord); // Perform translation to update totalSteps
        return totalSteps; // This is where the error occurs
    }
}
