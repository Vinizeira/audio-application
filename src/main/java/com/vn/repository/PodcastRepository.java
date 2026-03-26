package com.vn.repository;

import com.vn.model.Podcast;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

public class PodcastRepository extends ITunesSearchRepository {
    private static final String PODCAST_QUERY = "media=podcast&entity=podcast";

    public PodcastRepository() {
    }

    public PodcastRepository(HttpClient httpClient) {
        super(httpClient);
    }

    public List<Podcast> searchPodcast(String search) throws IOException, InterruptedException {
        List<Podcast> podcasts = new ArrayList<>();

        for (JSONObject item : searchResults(search, PODCAST_QUERY)) {
            String podcastName = item.optString("collectionName", item.optString("trackName", "")).trim();
            String episodeTitle = item.optString("trackName", podcastName).trim();
            String artist = item.optString("artistName", "").trim();
            int duration = item.optInt("trackTimeMillis", 0);
            int episodeNumber = item.optInt("trackCount", 0);

            if (podcastName.isEmpty() || artist.isEmpty()) {
                continue;
            }

            podcasts.add(new Podcast(podcastName, artist, duration, episodeTitle, episodeNumber));
        }

        return podcasts;
    }
}
