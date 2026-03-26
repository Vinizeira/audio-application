package com.vn.service;

import com.vn.model.Audio;
import com.vn.model.Playable;
import com.vn.repository.ITunesSearchException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AudioWorkflowService {
    private final MenuService menuService;
    private final MusicService musicService;
    private final PodcastService podcastService;
    private final AudioManagerService audioManagerService;
    private final FavoritesService favoritesService;

    public AudioWorkflowService(
            MenuService menuService,
            MusicService musicService,
            PodcastService podcastService,
            AudioManagerService audioManagerService,
            FavoritesService favoritesService
    ) {
        this.menuService = menuService;
        this.musicService = musicService;
        this.podcastService = podcastService;
        this.audioManagerService = audioManagerService;
        this.favoritesService = favoritesService;
    }

    public boolean handleOption(int option, Scanner scanner, List<Playable> audios) {
        return switch (option) {
            case 1 -> {
                handleSearch(scanner, audios, "Music", musicService::searchMusic);
                yield true;
            }
            case 2 -> {
                handleSearch(scanner, audios, "Podcast", podcastService::searchPodcast);
                yield true;
            }
            case 3 -> {
                menuService.printFavorites(favoritesService.getFavorites(audios));
                yield true;
            }
            case 0 -> {
                System.out.println("Exiting...");
                yield false;
            }
            default -> {
                System.out.println("Invalid option!");
                yield true;
            }
        };
    }

    private <T extends Audio> void handleSearch(
            Scanner scanner,
            List<Playable> audios,
            String type,
            SearchOperation<T> searchOperation
    ) {
        String query = menuService.readSearchQuery(scanner, type);
        if (query == null) {
            return;
        }

        List<T> results;
        try {
            results = searchOperation.search(query);
        } catch (ITunesSearchException e) {
            menuService.printError(e.getMessage());
            return;
        } catch (IOException e) {
            menuService.printError("Could not search " + type.toLowerCase() + " right now.");
            return;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            menuService.printError(type + " search was interrupted.");
            return;
        }

        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        menuService.displayList(results);
        int choice = menuService.readChoice(scanner, results.size());
        if (choice == -1) {
            return;
        }

        int rating = menuService.readRating(scanner);
        try {
            AudioUpdateResult result = audioManagerService.addOrUpdateAudio(audios, results.get(choice - 1), rating);
            System.out.println(result.added()
                    ? "Added:"
                    : "Updated existing " + type.toLowerCase() + " rating:");
            menuService.printAudioInfo(result.audio());
        } catch (IOException e) {
            menuService.printError("Could not save history.");
        }
    }

    @FunctionalInterface
    private interface SearchOperation<T extends Audio> {
        List<T> search(String query) throws IOException, InterruptedException;
    }
}
