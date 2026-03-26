package com.vn.service;

import com.vn.model.Music;
import com.vn.model.Playable;
import com.vn.model.Podcast;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    public void printMenu() {
        System.out.println("\n====================================");
        System.out.println("🎧 Audio Search Application");
        System.out.println("====================================");
        System.out.println("1 - 🎵 Search Music");
        System.out.println("2 - 🎙️ Search Podcast");
        System.out.println("3 - ⭐ Show Favorites");
        System.out.println("0 - 🚪 Exit");
        System.out.println("====================================");
    }

    public int readOption(Scanner sc) {
        System.out.print("Choose an option: ");
        String input = sc.nextLine();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
            return -1;
        }
    }

    public String readSearchQuery(Scanner sc, String type) {
        System.out.print("Enter the " + type + " name: ");
        String query = sc.nextLine().trim();
        if (query.isEmpty()) {
            System.out.println("Search cannot be empty.");
            return null;
        }
        return query;
    }

    public void displayList(List<? extends Playable> list) {
        System.out.println("\n📋 Search Results");
        System.out.println("--------------------");
        for (int i = 0; i < list.size(); i++) {
            Playable audio = list.get(i);

            if (audio instanceof Music music) {
                System.out.println((i + 1) + " - 🎵 " + music.getTitle() + " | " + music.getArtist() + " | " + music.getAlbum());
            } else if (audio instanceof Podcast podcast) {
                System.out.println((i + 1) + " - 🎙️ " + podcast.getTitle() + " | " + podcast.getHost() + " | Episode " + podcast.getEpisodeNumber());
            }
        }
        System.out.println("--------------------");
    }

    public int readChoice(Scanner sc, int max) {
        while (true) {
            System.out.print("Choose one (1-" + max + ", or 0 to cancel): ");
            String choiceInput = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice!");
                continue;
            }

            if (choice == 0) {
                System.out.println("Selection canceled.");
                return -1;
            }

            if (choice < 1 || choice > max) {
                System.out.println("Choice out of range!");
                continue;
            }

            return choice;
        }
    }

    public int readRating(Scanner sc) {
        while (true) {
            System.out.print("Rate this audio (0-5): ");
            String ratingInput = sc.nextLine();

            int rating;
            try {
                rating = Integer.parseInt(ratingInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid rating! Please enter a number from 0 to 5.");
                continue;
            }

            if (rating < 0 || rating > 5) {
                System.out.println("Rating out of range! Please enter a number from 0 to 5.");
                continue;
            }

            return rating;
        }
    }

    public void printAudioInfo(Playable audio) {
        System.out.println("--------------------");
        System.out.println("Title: " + audio.getTitle());
        if (audio instanceof Music m) {
            System.out.println("Artist: " + m.getArtist());
            System.out.println("Album: " + m.getAlbum());
            System.out.println("Genre: " + m.getGenre());
            System.out.println("Duration: " + m.getFormattedDuration());
        } else if (audio instanceof Podcast p) {
            System.out.println("Host: " + p.getHost());
            System.out.println("Episode title: " + p.getEpisodeTitle());
            System.out.println("Episode: " + p.getEpisodeNumber());
            System.out.println("Duration: " + p.getFormattedDuration());
        }
        System.out.println("Rating: " + audio.getRating());
    }

    public void printFavorites(List<Playable> favorites) {
        System.out.println("\n⭐ Favorites");
        System.out.println("--------------------");
        if (favorites.isEmpty()) {
            System.out.println("No favorites yet!");
            return;
        }

        for (Playable favorite : favorites) {
            printAudioInfo(favorite);
        }
    }
}
