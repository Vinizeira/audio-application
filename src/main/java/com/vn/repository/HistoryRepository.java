package com.vn.repository;

import com.vn.model.Music;
import com.vn.model.Podcast;
import com.vn.model.Playable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private static final Path DATA_DIRECTORY = Path.of("data");
    private static final Path HISTORY_FILE = DATA_DIRECTORY.resolve("History.json");

    public void saveMusic(Music music) throws IOException {
        if (music == null) return;
        List<Playable> audios = new ArrayList<>();
        audios.addAll(loadMusics());
        audios.addAll(loadPodcasts());
        audios.add(music);
        saveAll(audios);
    }

    public void savePodcast(Podcast podcast) throws IOException {
        if (podcast == null) return;
        List<Playable> audios = new ArrayList<>();
        audios.addAll(loadMusics());
        audios.addAll(loadPodcasts());
        audios.add(podcast);
        saveAll(audios);
    }

    public void saveAll(List<Playable> audios) throws IOException {
        JSONArray musicsArray = new JSONArray();
        JSONArray podcastsArray = new JSONArray();

        for (Playable audio : audios) {
            if (audio instanceof Music music) {
                musicsArray.put(toJson(music));
            } else if (audio instanceof Podcast podcast) {
                podcastsArray.put(toJson(podcast));
            }
        }

        JSONObject json = new JSONObject();
        json.put("musics", musicsArray);
        json.put("podcasts", podcastsArray);
        writeJson(json);
    }

    public List<Music> loadMusics() throws IOException {
        JSONObject json = loadJson();
        List<Music> musics = new ArrayList<>();
        if (json.has("musics")) {
            JSONArray arr = json.getJSONArray("musics");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject m = arr.getJSONObject(i);
                Music music = new Music(
                        m.getString("title"),
                        m.getString("artist"),
                        m.getString("album"),
                        m.getString("genre"),
                        m.getInt("duration")
                );
                music.setRating(m.getInt("rating"));
                musics.add(music);
            }
        }
        return musics;
    }

    public List<Podcast> loadPodcasts() throws IOException {
        JSONObject json = loadJson();
        List<Podcast> podcasts = new ArrayList<>();
        if (json.has("podcasts")) {
            JSONArray arr = json.getJSONArray("podcasts");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject p = arr.getJSONObject(i);
                Podcast podcast = new Podcast(
                        p.getString("title"),
                        p.getString("host"),
                        p.getInt("duration"),
                        p.optString("episodeTitle", ""),
                        p.optInt("episodeNumber", 0)
                );
                podcast.setRating(p.getInt("rating"));
                podcasts.add(podcast);
            }
        }
        return podcasts;
    }

    private JSONObject toJson(Music music) {
        JSONObject musicJson = new JSONObject();
        musicJson.put("title", music.getTitle());
        musicJson.put("artist", music.getArtist());
        musicJson.put("album", music.getAlbum());
        musicJson.put("genre", music.getGenre());
        musicJson.put("duration", music.getDuration());
        musicJson.put("rating", music.getRating());
        return musicJson;
    }

    private JSONObject toJson(Podcast podcast) {
        JSONObject podcastJson = new JSONObject();
        podcastJson.put("title", podcast.getTitle());
        podcastJson.put("host", podcast.getHost());
        podcastJson.put("duration", podcast.getDuration());
        podcastJson.put("rating", podcast.getRating());
        podcastJson.put("episodeTitle", podcast.getEpisodeTitle());
        podcastJson.put("episodeNumber", podcast.getEpisodeNumber());
        return podcastJson;
    }

    private JSONObject loadJson() throws IOException {
        File file = HISTORY_FILE.toFile();
        if (!file.exists()) return new JSONObject();
        String content = Files.readString(HISTORY_FILE);
        return new JSONObject(content.isEmpty() ? "{}" : content);
    }

    private void writeJson(JSONObject json) throws IOException {
        Files.createDirectories(DATA_DIRECTORY);
        Path target = HISTORY_FILE;
        Path temp = DATA_DIRECTORY.resolve("History.json.tmp");
        Files.writeString(temp, json.toString(4));
        Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
