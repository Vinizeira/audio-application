package com.vn.service;

import com.vn.model.Music;
import com.vn.model.Playable;
import com.vn.model.Podcast;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FavoritesServiceTest {
    private final FavoritesService favoritesService = new FavoritesService();

    @Test
    void shouldReturnOnlyAudiosRatedThreeOrMore() {
        Music favoriteMusic = new Music("Song A", "Artist", "Album", "Pop", 180_000);
        favoriteMusic.setRating(4);

        Podcast neutralPodcast = new Podcast("Pod A", "Host", 3_000, "Episode", 1);
        neutralPodcast.setRating(2);

        Music favoriteMusicTwo = new Music("Song B", "Artist", "Album", "Pop", 200_000);
        favoriteMusicTwo.setRating(3);

        List<Playable> favorites = favoritesService.getFavorites(List.of(favoriteMusic, neutralPodcast, favoriteMusicTwo));

        assertEquals(List.of(favoriteMusic, favoriteMusicTwo), favorites);
    }
}
