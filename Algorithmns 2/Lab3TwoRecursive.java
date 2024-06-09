public class Lab3TwoRecursive {
    public static void main(String[] args) {
        String s1 = "abcdhij";
        String s2 = "efg";
        
        // Combine the strings
        String word = s1 + s2;
        
        // Convert the combined string to an array of ASCII values
        int[] num = new int[word.length()];
        for (int i = 0; i < word.length(); i++) {
            num[i] = word.charAt(i);
        }
    
        System.out.println("Before");
        printArray(num);
        
        mergeSort(num);

        System.out.println("\nAfter");
        printArray(num);
    }

    public static void mergeSort(int [] inputArray){
        
        int inputLength = inputArray.length;
        if(inputLength < 2){
            return; //Already sorted nothing for us to do
        }

        int midIndex = inputLength / 2;
        int [] leftHalf = new int[midIndex];
        int [] rightHalf = new int[inputLength - midIndex];

        for(int i = 0; i<midIndex; i++){ //Filling up the elements on leftHalf
            leftHalf[i] = inputArray[i];
        }

        for(int i=midIndex; i<inputLength; i++){ //Filling up the right half elements
            rightHalf[i - midIndex] = inputArray[i];
        }


        mergeSort(leftHalf);
        mergeSort(rightHalf);

        merge(inputArray, leftHalf, rightHalf);

    }
    private static void merge(int[] inputArray, int[] leftHalf, int[ ] rightHalf) {
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        int i = 0, j = 0, k = 0;

        while(i< leftSize && j< rightSize){
            if(leftHalf[i] <= rightHalf[j]){
                inputArray[k] = leftHalf[i];
                i++;
            }
            else{
                inputArray[k] = rightHalf[j];
                j++;
            }
            k++;
        }
        while(i<leftSize){
            inputArray[k] = leftHalf[i];
            i++;
            k++;
        }
        while(j<rightSize){
            inputArray[k] = rightHalf[j];
            j++;
            k++;
        }
    }

    public static void printArray(int [] numbers){
        char [] e = new char[numbers.length];
        for(int i=0; i<numbers.length; i++){
            e[i] = (char) numbers[i];
        }
        
        String result = String.valueOf(e);
        System.out.println(result);
        
        // for(int i=0; i<numbers.length; i++){
        //     System.out.print(numbers[i]+" ");
        // }
    }
}