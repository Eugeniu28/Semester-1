package Lab7EngSpan;

public class TreeNode {

    String engWord;
    String spanWord;
    long frequency;
    TreeNode left, right;

    public TreeNode(String englishWord, String spanishWord, long frequency) {
        this.engWord = englishWord;
        this.spanWord = spanishWord;
        this.frequency = frequency;
        left = right = null;
    }

}
