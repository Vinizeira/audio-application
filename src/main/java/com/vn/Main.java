package com.vn;

import com.vn.model.Music;
import com.vn.model.Playable;
import com.vn.model.Podcast;
import com.vn.service.AudioManagerService;
import com.vn.service.AudioUpdateResult;
import com.vn.service.FavoritesService;
import com.vn.service.MenuService;
import com.vn.service.MusicService;
import com.vn.service.PodcastService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuService menuService = new MenuService();
        MusicService musicService = new MusicService();
        PodcastService podcastService = new PodcastService();
        AudioManagerService audioManagerService = new AudioManagerService();
        FavoritesService favoritesService = new FavoritesService();
        List<Playable> audios = new ArrayList<>();

        try {
            audios.addAll(audioManagerService.loadAudios());
        } catch (IOException e) {
            System.out.println("Could not load history. Starting with an empty list.");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nSaving before closing...");
            try {
                audioManagerService.saveHistory(audios);
            } catch (IOException e) {
                System.out.println("Error saving audio during shutdown.");
            }
        }));

        while (true) {
            menuService.printMenu();
            int option = menuService.readOption(sc);
            if (option == -1) {
                continue;
            }

            if (option == 0) {
                System.out.println("Exiting...");
                break;
            }

            switch (option) {
                case 1 -> {
                    String query = menuService.readSearchQuery(sc, "Music");
                    if (query == null) {
                        continue;
                    }

                    List<Music> musics;
                    try {
                        musics = musicService.searchMusic(query);
                    } catch (IOException e) {
                        System.out.println("Could not search music right now.");
                        continue;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Music search was interrupted.");
                        return;
                    }

                    if (!musics.isEmpty()) {
                        menuService.displayList(musics);
                        int choice = menuService.readChoice(sc, musics.size());
                        if (choice != -1) {
                            int rating = menuService.readRating(sc);
                            handleMusicSelection(audios, musics.get(choice - 1), rating, audioManagerService, menuService);
                        }
                    } else {
                        System.out.println("No results found.");
                    }
                }
                case 2 -> {
                    String query = menuService.readSearchQuery(sc, "Podcast");
                    if (query == null) {
                        continue;
                    }

                    List<Podcast> podcasts;
                    try {
                        podcasts = podcastService.searchPodcast(query);
                    } catch (IOException e) {
                        System.out.println("Could not search podcast right now.");
                        continue;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Podcast search was interrupted.");
                        return;
                    }

                    if (!podcasts.isEmpty()) {
                        menuService.displayList(podcasts);
                        int choice = menuService.readChoice(sc, podcasts.size());
                        if (choice != -1) {
                            int rating = menuService.readRating(sc);
                            handlePodcastSelection(audios, podcasts.get(choice - 1), rating, audioManagerService, menuService);
                        }
                    } else {
                        System.out.println("No results found.");
                    }
                }
                case 3 -> menuService.printFavorites(favoritesService.getFavorites(audios));
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void handleMusicSelection(
            List<Playable> audios,
            Music selected,
            int rating,
            AudioManagerService audioManagerService,
            MenuService menuService
    ) {
        try {
            AudioUpdateResult result = audioManagerService.addOrUpdateMusic(audios, selected, rating);
            System.out.println(result.added() ? "Added:" : "Updated existing music rating:");
            menuService.printAudioInfo(result.audio());
        } catch (IOException e) {
            System.out.println("Could not save history.");
        }
    }

    private static void handlePodcastSelection(
            List<Playable> audios,
            Podcast selected,
            int rating,
            AudioManagerService audioManagerService,
            MenuService menuService
    ) {
        try {
            AudioUpdateResult result = audioManagerService.addOrUpdatePodcast(audios, selected, rating);
            System.out.println(result.added() ? "Added:" : "Updated existing podcast rating:");
            menuService.printAudioInfo(result.audio());
        } catch (IOException e) {
            System.out.println("Could not save history.");
        }
    }
}
