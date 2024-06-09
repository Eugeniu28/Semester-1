import java.util.Scanner;
public class Lab3Two {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String a = "abcdhij";
        String b = "efg";
        String s = a+b;
        
        scan.close();

        //Converting the String into ASCII Values
        int [] num = new int[s.length()];
        for(int i=0; i<num.length; i++){
            num[i] += s.charAt(i);
        }
        for(int x : num){
            System.out.print(x+" ");
        }

        //Bubble sort Sorting 
        for(int i=0; i<num.length; i++){
            for(int j = 0; j<num.length-1; j++){ 
                if(num[j]>num[j+1]){
                    int temp = num[j];
                    num [j] = num[j+1];
                    num [j+1] = temp;
                }
            }
        }
        System.out.println();
        for(int x : num){
            System.out.print(x+" ");
        }

        //Converting from ASCII Value back into String
        char [] e = new char[num.length];
        for(int i=0; i<num.length; i++){
            e[i] = (char) num[i];
        }
        System.out.println();
        
        String result = String.valueOf(e);
        System.out.println(result);
    }
}
