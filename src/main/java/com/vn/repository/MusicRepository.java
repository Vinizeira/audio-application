package com.vn.repository;

import com.vn.model.Music;
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

public class MusicRepository {
    public List<Music> searchMusic(String search) throws IOException, InterruptedException {
        String encoded = URLEncoder.encode(search, StandardCharsets.UTF_8);
        String address = "https://itunes.apple.com/search?term=" + encoded + "&entity=musicTrack";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Unexpected response from music API: " + response.statusCode());
        }

        JSONObject json = new JSONObject(response.body());
        JSONArray results = json.getJSONArray("results");

        List<Music> musics = new ArrayList<>();
        for (int i = 0; i < Math.min(5, results.length()); i++) {
            JSONObject item = results.getJSONObject(i);
            String title = item.optString("trackName", "").trim();
            String artist = item.optString("artistName", "").trim();
            String album = item.optString("collectionName", "").trim();
            String genre = item.optString("primaryGenreName", "Unknown").trim();
            int durationMillis = item.optInt("trackTimeMillis", 0);

            if (title.isEmpty() || artist.isEmpty() || album.isEmpty()) {
                continue;
            }

            musics.add(new Music(title, artist, album, genre, durationMillis));
        }
        return musics;
    }
}
