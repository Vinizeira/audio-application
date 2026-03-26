package com.vn.repository;

import com.vn.model.Music;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MusicRepositoryTest {
    @Test
    void shouldMapOnlyValidMusicResultsAndRespectLimit() throws IOException, InterruptedException {
        String body = """
                {
                  "results": [
                    {"trackName":"Song 1","artistName":"Artist 1","collectionName":"Album 1","primaryGenreName":"Rock","trackTimeMillis":1000},
                    {"trackName":"Song 2","artistName":"Artist 2","collectionName":"Album 2","primaryGenreName":"Pop","trackTimeMillis":2000},
                    {"trackName":"Song 3","artistName":"Artist 3","collectionName":"Album 3","primaryGenreName":"Jazz","trackTimeMillis":3000},
                    {"trackName":"Song 4","artistName":"Artist 4","collectionName":"Album 4","primaryGenreName":"Metal","trackTimeMillis":4000},
                    {"trackName":"Song 5","artistName":"Artist 5","collectionName":"Album 5","primaryGenreName":"Hip Hop","trackTimeMillis":5000},
                    {"trackName":"Song 6","artistName":"Artist 6","collectionName":"Album 6","primaryGenreName":"Soul","trackTimeMillis":6000},
                    {"trackName":"","artistName":"Artist 7","collectionName":"Album 7","primaryGenreName":"Soul","trackTimeMillis":7000}
                  ]
                }
                """;

        HttpClientStub client = new HttpClientStub(200, body);
        MusicRepository repository = new MusicRepository(client);

        List<Music> musics = repository.searchMusic("Linkin Park");

        assertEquals(5, musics.size());
        assertEquals("Song 1", musics.get(0).getTitle());
        assertEquals("Artist 1", musics.get(0).getArtist());
        assertTrue(client.lastRequestedUri().toString().contains("term=Linkin+Park"));
        assertTrue(client.lastRequestedUri().toString().contains("entity=musicTrack"));
    }

    @Test
    void shouldThrowSpecificExceptionWhenApiReturnsUnexpectedStatus() {
        MusicRepository repository = new MusicRepository(new HttpClientStub(500, "{\"results\":[]}"));

        Exception exception = assertThrows(ITunesSearchException.class, () -> repository.searchMusic("Muse"));

        assertTrue(exception.getMessage().contains("status 500"));
    }

    @Test
    void shouldThrowSpecificExceptionWhenApiReturnsInvalidJson() {
        MusicRepository repository = new MusicRepository(new HttpClientStub(200, "not-json"));

        Exception exception = assertThrows(ITunesSearchException.class, () -> repository.searchMusic("Muse"));

        assertInstanceOf(ITunesSearchException.class, exception);
        assertTrue(exception.getMessage().contains("invalid response"));
    }

    @Test
    void shouldThrowSpecificExceptionWhenConnectionFails() {
        MusicRepository repository = new MusicRepository(new HttpClientStub(new IOException("Connection reset")));

        Exception exception = assertThrows(ITunesSearchException.class, () -> repository.searchMusic("Muse"));

        assertTrue(exception.getMessage().contains("Could not connect"));
    }
}
