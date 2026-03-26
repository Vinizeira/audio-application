package com.vn.repository;

import com.vn.model.Podcast;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PodcastRepositoryTest {
    @Test
    void shouldMapPodcastResultsUsingFallbackFields() throws IOException, InterruptedException {
        String body = """
                {
                  "results": [
                    {"collectionName":"Podcast 1","trackName":"Episode 1","artistName":"Host 1","trackTimeMillis":1000,"trackCount":12},
                    {"trackName":"Podcast 2","artistName":"Host 2","trackTimeMillis":2000,"trackCount":8},
                    {"collectionName":"","trackName":"","artistName":"Host 3","trackTimeMillis":3000,"trackCount":5}
                  ]
                }
                """;

        HttpClientStub client = new HttpClientStub(200, body);
        PodcastRepository repository = new PodcastRepository(client);

        List<Podcast> podcasts = repository.searchPodcast("Java");

        assertEquals(2, podcasts.size());
        assertEquals("Podcast 1", podcasts.get(0).getTitle());
        assertEquals("Episode 1", podcasts.get(0).getEpisodeTitle());
        assertEquals("Podcast 2", podcasts.get(1).getTitle());
        assertTrue(client.lastRequestedUri().toString().contains("media=podcast"));
    }

    @Test
    void shouldThrowSpecificExceptionWhenPodcastApiReturnsUnexpectedStatus() {
        PodcastRepository repository = new PodcastRepository(new HttpClientStub(503, "{\"results\":[]}"));

        Exception exception = assertThrows(ITunesSearchException.class, () -> repository.searchPodcast("news"));

        assertTrue(exception.getMessage().contains("status 503"));
    }

    @Test
    void shouldThrowSpecificExceptionWhenPodcastApiReturnsInvalidJson() {
        PodcastRepository repository = new PodcastRepository(new HttpClientStub(200, "{"));

        Exception exception = assertThrows(ITunesSearchException.class, () -> repository.searchPodcast("news"));

        assertInstanceOf(ITunesSearchException.class, exception);
        assertTrue(exception.getMessage().contains("invalid response"));
    }

    @Test
    void shouldThrowSpecificExceptionWhenPodcastConnectionFails() {
        PodcastRepository repository = new PodcastRepository(new HttpClientStub(new IOException("Host unreachable")));

        Exception exception = assertThrows(ITunesSearchException.class, () -> repository.searchPodcast("news"));

        assertTrue(exception.getMessage().contains("Could not connect"));
    }
}
