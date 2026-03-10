package com.vn.challenge2;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter tournament name: ");
        String tournamentName = sc.nextLine();

        HashMap<String, ArrayList<Player>> tournaments = new HashMap<>();
        tournaments.put(tournamentName, new ArrayList<>());
        ArrayList<Player> players = tournaments.get(tournamentName);

        int choice = 1;
        while (choice != 0) {
            System.out.println("Enter player name: ");
            String name = sc.nextLine();
            int score = 0;
            try {
            System.out.println("Enter score: ");
            score = sc.nextInt();
            sc.nextLine(); //clear buffer
        }
            catch(InputMismatchException e) {
            System.out.println("Invalid input! Score must be a number.");
            sc.nextLine(); // clear buffer
            continue; //come back to start
        }
            finally {
            System.out.println("Score input processed.");
        }
            //check empty name
            if(name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                continue;
            }

            //check negative score
            if (score < 0){
                System.out.println("Score cannot be negative!");
                continue;
            }
            // Check Duplicate Players
            if (players.contains(new Player(name, score, tournamentName))) {
                System.out.println("Player already exists!");
                continue;
            }

            Player player = new Player(name, score, tournamentName);
            players.add(player);

            System.out.println("Enter 0 to exit or 1 to continue or 2 to remove player: ");
            choice = sc.nextInt();
            sc.nextLine(); //clear buffer

            if (choice == 2) {
                System.out.println("Enter player name to remove: ");
                String removeName = sc.nextLine();
                players.remove(new Player(removeName, 0, tournamentName));
                System.out.println("Player removed!");
            }
        }

        System.out.println("""
        How do you want to sort the ranking?
        1 - Score Descending
        2 - Score Ascending
        3 - Name Alphabetically
        """);
        int sortChoice = sc.nextInt();
        sc.nextLine();
        if (sortChoice == 1) {
            Collections.sort(players); // already sorted descending by compareTo
        } else if (sortChoice == 2) {
            players.sort(Comparator.comparing(Player::getScore));
        } else if (sortChoice == 3) {
            players.sort(Comparator.comparing(Player::getName));
        }

        System.out.println("Total players: " + players.size());
        System.out.println("\n🏆 RANKING - "+tournamentName + ":");
        players.forEach(System.out::println);
        System.out.println("🏆 The champion of the " + tournamentName + " is: " + players.get(0));
    }
}
