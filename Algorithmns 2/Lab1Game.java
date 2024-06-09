import java.util.*;

public class Lab1Game {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    int participants = 22;
    System.out.println(
      "Enter a number to choose traitors any number less than 22"
    );
    int traitors = scan.nextInt(); // Number of chosen traitors by User
    int faithful = participants - traitors;
    scan.close();
    simulateGame(faithful, traitors);
    //System.out.println("The chances of traitors Winning is " + monteCarloTraitorsWin(traitors,simulations));

  }

  public static void simulateGame(int initialFaithful, int initialTraitors) {
    int simulations = 1000000;

    Random random = new Random();
    int faithfulWin = 0;
    int traitorsWin = 0;

    for (int i = 0; i < simulations; i++) {
      //This makes sure every game is started with same amount of players
      int faithful = initialFaithful;
      int traitors = initialTraitors;

      //Loop to run the One Simulation
      while (faithful + traitors > 2) {
        // This ensures that any player including both faithful and traitors can be selected for elimination
        int voteToEliminate = random.nextInt(faithful + traitors);
        // If traitors fail to eliminate their target they will be eliminated
        if (voteToEliminate < traitors) {
          traitors--;
        } else {
          faithful--;
        }

        //Checks if any traitors left in the game and eliminates a faithful
        if (traitors > 0 && faithful > 0) {
          faithful--;
        }
      }

      //If there's one traitor left then traitors win otherwise faithful win
      if (traitors == 0) {
        faithfulWin++;
      } else {
        traitorsWin++;
      }
    }

    System.out.println("After " + simulations + " simulations:");
    System.out.println(
      "Faithful wins: " +
      faithfulWin +
      " (" +
      ((double) faithfulWin / simulations * 100) +
      "%)"
    );
    System.out.println(
      "Traitor wins: " +
      traitorsWin +
      " (" +
      ((double) traitorsWin / simulations * 100) +
      "%)"
    );
  }
}
// n are selected as Traitors
// faithful have to figure out who the traitors are and vote to eliminate
// one faithful is also eliminated during the night by traitors
// any player (faithful || traitor) is eliminated by VOTE and one faithfulPlayer is elimaneted randomly during the night by traitors
// Game continues until 2 people left If 2 left are faithful then faithful won, otherwise traitors won.
// Given n traitors, calculates Monte Carlo probability that the traitors will win
// Assume that the faithful are stupid and always vote randomly
// Assume that the traitors select one of the faithful randomly, and all
//    of them vote for that same person.
