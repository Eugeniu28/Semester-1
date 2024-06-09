import java.util.*;

public class Lab6MonteCarlo {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int simulations = 1000000;
    System.out.println("How much did you pay for ticket");
    int price = scan.nextInt();
    System.out.println("How many lines rows you want on your ticket");
    int rows = scan.nextInt();

    // System.out.println(((double) tickets / simulations * 100) + "%)");
    scan.close();
    double avg = lottery(price, simulations, rows);
    System.out.println("Expected return: " + avg + "%");
  }

  public static double lottery(int price, int simulations, int rows) {
    Random random = new Random();
    double totalWinnings = 0;

    // Generate random numbers for our ticket
    int[][] ticket = new int[rows][7];
    for (int i = 0; i < rows; i++) {
      ticket[i] = generateTicket(random);
    }

    // Generate random numbers for our Winning Numbers
    int[] winningNumbers = new int[5];
    for (int i = 0; i < 5; i++) {
      winningNumbers[i] = random.nextInt(50) + 1; // range from 1 to 50
    }

    // Loop for simulations
    for (int i = 0; i < simulations; i++) {
      for (int j = 0; j < rows; j++) {
        // Check for matches and calculate winnings
        int matches = countMatches(ticket[j], winningNumbers);
        totalWinnings +=
          calculateWinnings(matches, hasTwoLuckyNumbers(ticket[j]));
      }
    }

    // Calculate average
    double averageReturn = (totalWinnings / simulations) / (price * rows) * 100;
    return averageReturn;
  }

  // Generate random ticket
  public static int[] generateTicket(Random random) {
    int[] ticket = new int[7];
    for (int i = 0; i < 5; i++) {
      ticket[i] = random.nextInt(50) + 1; // range from 1 to 50
    }
    // Add two lucky numbers
    ticket[5] = random.nextInt(12) + 1; // range from 1 to 12
    ticket[6] = random.nextInt(12) + 1;
    return ticket;
  }

  // Count the number of matches between ticket and winning numbers
  public static int countMatches(int[] ticket, int[] winningNumbers) {
    Arrays.sort(ticket);
    Arrays.sort(winningNumbers);

    int ticketIndex = 0;
    int winningIndex = 0;
    int matches = 0;

    while (
      ticketIndex < ticket.length && winningIndex < winningNumbers.length
    ) {
      if (ticket[ticketIndex] == winningNumbers[winningIndex]) {
        matches++;
        ticketIndex++;
        winningIndex++;
      } else if (ticket[ticketIndex] < winningNumbers[winningIndex]) {
        ticketIndex++;
      } else {
        winningIndex++;
      }
    }

    return matches;
  }

  // Check if ticket has two lucky numbers
  public static boolean hasTwoLuckyNumbers(int[] ticket) {
    return ticket[5] != 0 && ticket[6] != 0;
  }

  // Calculate winnings based on the number of matches and lucky numbers
  public static double calculateWinnings(
    int matches,
    boolean hasTwoLuckyNumbers
  ) {
    if (matches == 5 && hasTwoLuckyNumbers) { // 5 + 2 lucky stars
      return 130_000_000;
    } else if (matches == 5 && !hasTwoLuckyNumbers) { // 5 + 1 star
      return 420_495;
    } else if (matches == 4 && hasTwoLuckyNumbers) { // 4 + 2 stars
      return 1_954;
    } else if (matches == 4 && !hasTwoLuckyNumbers) { // 4 + 1 star
      return 161;
    } else if (matches == 3 && hasTwoLuckyNumbers) { // 3 + 2 stars
      return 65;
    } else if (matches == 3 && !hasTwoLuckyNumbers) { // 3 + 1 star
      return 13;
    } else if (matches == 2 && hasTwoLuckyNumbers) { // 2 + 2 stars
      return 16;
    } else {
      return 0; // Return 0 if no matches or other winning combinations
    }
  }
}
