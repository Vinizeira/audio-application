package com.vn.service;

import com.vn.model.Music;
import com.vn.model.Playable;
import com.vn.model.Podcast;
import com.vn.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioManagerServiceTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldAddNewAudioWhenItDoesNotExist() throws IOException {
        AudioManagerService service = new AudioManagerService(new HistoryRepository(tempDir.resolve("History.json")));
        List<Playable> audios = new ArrayList<>();
        Music selected = new Music("Song A", "Artist", "Album", "Pop", 100_000);

        AudioUpdateResult result = service.addOrUpdateAudio(audios, selected, 5);

        assertTrue(result.added());
        assertEquals(1, audios.size());
        assertEquals(5, audios.get(0).getRating());
    }

    @Test
    void shouldUpdateExistingAudioInsteadOfAddingDuplicate() throws IOException {
        AudioManagerService service = new AudioManagerService(new HistoryRepository(tempDir.resolve("History.json")));
        Music existing = new Music("Song A", "Artist", "Album", "Pop", 100_000);
        existing.setRating(1);
        List<Playable> audios = new ArrayList<>(List.of(existing));

        Music selected = new Music("song a", "artist", "album", "Pop", 100_000);
        AudioUpdateResult result = service.addOrUpdateAudio(audios, selected, 4);

        assertFalse(result.added());
        assertEquals(1, audios.size());
        assertEquals(4, existing.getRating());
    }

    @Test
    void shouldKeepDifferentPodcastEpisodesSeparated() throws IOException {
        AudioManagerService service = new AudioManagerService(new HistoryRepository(tempDir.resolve("History.json")));
        Podcast firstEpisode = new Podcast("Tech Talk", "Ana", 3_000, "Episode 1", 1);
        List<Playable> audios = new ArrayList<>(List.of(firstEpisode));

        Podcast secondEpisode = new Podcast("Tech Talk", "Ana", 3_000, "Episode 2", 2);
        AudioUpdateResult result = service.addOrUpdateAudio(audios, secondEpisode, 5);

        assertTrue(result.added());
        assertEquals(2, audios.size());
    }
}
