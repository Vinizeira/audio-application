package com.vn;

import com.vn.model.Playable;
import com.vn.service.AudioManagerService;
import com.vn.service.AudioWorkflowService;
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
        Scanner scanner = new Scanner(System.in);
        MenuService menuService = new MenuService();
        MusicService musicService = new MusicService();
        PodcastService podcastService = new PodcastService();
        AudioManagerService audioManagerService = new AudioManagerService();
        FavoritesService favoritesService = new FavoritesService();
        AudioWorkflowService audioWorkflowService = new AudioWorkflowService(
                menuService,
                musicService,
                podcastService,
                audioManagerService,
                favoritesService
        );
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
            int option = menuService.readOption(scanner);
            if (option == -1) {
                continue;
            }

            if (!audioWorkflowService.handleOption(option, scanner, audios)) {
                break;
            }
        }
    }
}
