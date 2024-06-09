import java.util.*;
public class Lab4LinkedList{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        LinkedList list = new LinkedList(); //Creating an Object list to hold the word entered by User
        while(true){
                System.out.println("Enter a word: ");
                String word = scan.nextLine();
                if(word.equalsIgnoreCase("end")){ //If user enters words End it breaks the Loop
                    list.printReversedList(list.head); //Prints the list backwards
                    break;
                }
                else{
                    list.insert(list, word);
                }
            }
        scan.close();
    }
}

class LinkedList {
    Link head;
    
    static class Link {
        String data; //data type
        Link next;   //Represent each element reference
        Link(String s) {
            data = s;
            next = null;
        }
    }
    public LinkedList insert(LinkedList list, String s) {
        Link newLink = new Link(s);  //takes element to be inserted 
        newLink.next = null;         

        if (list.head == null) {
            list.head = newLink; //represents the start of the point 
        }
        else {
            Link last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newLink;
        }
        return list;
    }
    public void printReversedList(Link head) {
        if (head == null) {return;}

        printReversedList(head.next);
        System.out.println(head.data);
    }
}