package com.vn.repository;

import com.vn.model.Music;
import com.vn.model.Playable;
import com.vn.model.Podcast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class HistoryRepositoryTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldPersistAndReloadDifferentAudioTypes() throws IOException {
        HistoryRepository repository = new HistoryRepository(tempDir.resolve("History.json"));

        Music music = new Music("Song A", "Artist", "Album", "Pop", 180_000);
        music.setRating(4);

        Podcast podcast = new Podcast("Podcast A", "Host", 3_000, "Episode 10", 10);
        podcast.setRating(5);

        repository.saveAll(List.of(music, podcast));
        List<Playable> loadedAudios = repository.loadAll();

        assertEquals(2, loadedAudios.size());
        assertInstanceOf(Music.class, loadedAudios.get(0));
        assertInstanceOf(Podcast.class, loadedAudios.get(1));
        assertEquals(4, loadedAudios.get(0).getRating());
        assertEquals(5, loadedAudios.get(1).getRating());
    }
}
