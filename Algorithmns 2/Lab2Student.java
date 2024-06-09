import java.util.Scanner;
public class Lab2Student{
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);

        String student = "PPALLLPPPPPPP";
        student = student.toLowerCase();
        int countL = 0;
        int consecutiveL = 0;
        int countA = 0;
        
        // for(int i=0; i<student.length(); i++){
        //     if(student.charAt(i)=='a'){
        //         countA++;
        //     }
            
        //     if(student.charAt(i)=='l'){
        //         countL++;
        //         if(countL >= 3){
        //             consecutiveL=1;
        //         }
                
        //     }else{
        //         countL = 0;
        //     }
        // }
        // if(countA<=2 && consecutiveL==0){
        //     System.out.println("Pass");
        // }

        // else{
        //     System.out.println("Fail");

        // }

        scan.close();        
        labAttendace(student,countL,consecutiveL,countA);
    }
    
    public static String labAttendace(String student, int countL, int consecutiveL, int countA){

        if(student.length()==0){
            if(countA<=2 && consecutiveL==0){
                System.out.println("Pass");
            }
    
            else{
                System.out.println("Fail");
    
            }
            return ""; //Stops from accessing an empty String at the end
        }
        
        if(student.charAt(0)=='a'){
                countA++;
            } 
        if(student.charAt(0)=='l'){
                countL++;
            if(countL >= 3){
                    consecutiveL=1;
                }
        }
        else{
            countL = 0;
        }
        return labAttendace(student.substring(1),countL,consecutiveL,countA);
    } 
}