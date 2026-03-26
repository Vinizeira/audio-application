package com.vn.repository;

import com.vn.model.Podcast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PodcastRepository {
    public List<Podcast> searchPodcast(String search) throws IOException, InterruptedException {
        String encoded = URLEncoder.encode(search, StandardCharsets.UTF_8);
        String address = "https://itunes.apple.com/search?term=" + encoded + "&media=podcast&entity=podcast";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Unexpected response from podcast API: " + response.statusCode());
        }

        JSONObject json = new JSONObject(response.body());
        JSONArray results = json.getJSONArray("results");

        List<Podcast> podcasts = new ArrayList<>();
        for (int i = 0; i < Math.min(5, results.length()); i++) {
            JSONObject item = results.getJSONObject(i);
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
