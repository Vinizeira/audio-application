package com.vn.repository;

import com.vn.model.Music;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

public class MusicRepository extends ITunesSearchRepository {
    private static final String MUSIC_QUERY = "entity=musicTrack";

    public MusicRepository() {
    }

    public MusicRepository(HttpClient httpClient) {
        super(httpClient);
    }

    public List<Music> searchMusic(String search) throws IOException, InterruptedException {
        List<Music> musics = new ArrayList<>();

        for (JSONObject item : searchResults(search, MUSIC_QUERY)) {
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
