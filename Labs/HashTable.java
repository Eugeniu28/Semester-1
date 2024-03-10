class HashTable {

    // Hashtable to store private array and total to keep track of collisions
    private String[] hashTable;
    private long total = 0;

    public HashTable(String[] input) {
        hashTable = input;
    }

    // Checks if a slot in the hashtable contains a specific value
    // Returns true otherwise it counts total and returns false
    public boolean check(int slot, String check) {
        if (hashTable[slot].equals(check)) {
            return true;
        } else {
            total++;
            return false;
        }
    }

    // Gets the number of collisions
    public long getTotal() {
        return total;
    }
}

class Solution {
    // The find method uses double hashing for collision
    // Takes 2 inputs the size + hashtable and the word to find the index.

    public int find(int size, HashTable mytable, String word) {
        int hash = hashFunction1(word, size); // Keep track of 1st hash
        int jumpsize = hashFunction2(word, size); // The jump size using double hashing
        int probe = 1; // Keeps track of number attempts made to solve collisions

        while (!mytable.check(hash, word)) {
            // Checks for Collisions, otherwise finds different slot
            hash = (hash + probe * jumpsize) % size;
            probe++;
        }
        return hash;
    }

    // Method to store words
    public String[] fill(int size, String[] array) {
        String[] hashtable = new String[size]; //

        // Loop to insert words
        for (String word : array) {
            int hash = hashFunction1(word, size); // Initial hash value for current word
            int stepSize = hashFunction2(word, size); // Calculates the 2nd hash function
            int probe = 1; // Keeps track of collisions and find empty slot

            while (hashtable[hash] != null && !hashtable[hash].isEmpty()) {
                // Check if Collision, if the current slot is occupied
                hash = (hash + probe * stepSize) % size;
                probe++;
            }
            hashtable[hash] = word;
        }
        return hashtable;
    }

    // Simple hash function: sum of ASCII values of characters
    private int hashFunction1(String word, int size) {
        int hash = 0;
        for (char c : word.toCharArray()) {
            hash += (int) c;
        }
        return hash % size;
    }

    // Second hash function for double hashing
    private int hashFunction2(String word, int size) {
        int hash = 0;

        for (char c : word.toCharArray()) {
            hash = (hash * 31) + c;
        }

        return (hash % size + size) % size; // Ensure the result is positive and within the size
    }

}
