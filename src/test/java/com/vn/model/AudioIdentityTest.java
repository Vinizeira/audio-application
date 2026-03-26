package com.vn.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioIdentityTest {
    @Test
    void musicShouldMatchByTitleArtistAndAlbumIgnoringCase() {
        Music left = new Music("Numb", "Linkin Park", "Meteora", "Rock", 120_000);
        Music right = new Music("numb", "linkin park", "meteora", "Rock", 120_000);

        assertTrue(left.hasSameIdentity(right));
    }

    @Test
    void podcastShouldMatchByTitleHostAndEpisodeNumber() {
        Podcast left = new Podcast("Tech Talk", "Ana", 3_000, "Episode 1", 1);
        Podcast right = new Podcast("tech talk", "ana", 5_000, "Other title", 1);

        assertTrue(left.hasSameIdentity(right));
    }

    @Test
    void differentAudioTypesShouldNotMatch() {
        Music music = new Music("Numb", "Linkin Park", "Meteora", "Rock", 120_000);
        Podcast podcast = new Podcast("Numb", "Host", 120_000, "Episode", 1);

        assertFalse(music.hasSameIdentity(podcast));
    }
}
