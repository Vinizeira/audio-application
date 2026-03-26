package com.vn.repository;

import com.vn.config.AppConfig;
import com.vn.model.Music;
import com.vn.model.Playable;
import com.vn.model.Podcast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private static final Path DEFAULT_HISTORY_FILE = AppConfig.HISTORY_FILE;

    private final Path historyFile;
    private final Path dataDirectory;

    public HistoryRepository() {
        this(DEFAULT_HISTORY_FILE);
    }

    public HistoryRepository(Path historyFile) {
        this.historyFile = historyFile;
        this.dataDirectory = historyFile.toAbsolutePath().getParent();
    }

    public void saveMusic(Music music) throws IOException {
        saveAudio(music);
    }

    public void savePodcast(Podcast podcast) throws IOException {
        saveAudio(podcast);
    }

    public void saveAll(List<Playable> audios) throws IOException {
        JSONObject root = new JSONObject();
        JSONArray musics = new JSONArray();
        JSONArray podcasts = new JSONArray();

        for (Playable audio : audios) {
            if (audio instanceof Music music) {
                musics.put(toJson(music));
            } else if (audio instanceof Podcast podcast) {
                podcasts.put(toJson(podcast));
            }
        }

        root.put("musics", musics);
        root.put("podcasts", podcasts);
        writeJson(root.toString(2));
    }

    public List<Playable> loadAll() throws IOException {
        List<Playable> audios = new ArrayList<>();
        audios.addAll(loadMusics());
        audios.addAll(loadPodcasts());
        return audios;
    }

    public List<Music> loadMusics() throws IOException {
        JSONObject root = loadRootJson();
        JSONArray musicsArray = root.optJSONArray("musics");
        List<Music> musics = new ArrayList<>();
        if (musicsArray == null) {
            return musics;
        }

        for (int i = 0; i < musicsArray.length(); i++) {
            JSONObject item = musicsArray.optJSONObject(i);
            if (item == null) {
                continue;
            }

            String title = item.optString("title", "").trim();
            String artist = item.optString("artist", "").trim();
            String album = item.optString("album", "").trim();
            if (title.isEmpty() || artist.isEmpty() || album.isEmpty()) {
                continue;
            }

            Music music = new Music(
                    title,
                    artist,
                    album,
                    item.optString("genre", "Unknown"),
                    item.optInt("duration", 0)
            );
            music.setRating(item.optInt("rating", 0));
            musics.add(music);
        }

        return musics;
    }

    public List<Podcast> loadPodcasts() throws IOException {
        JSONObject root = loadRootJson();
        JSONArray podcastsArray = root.optJSONArray("podcasts");
        List<Podcast> podcasts = new ArrayList<>();
        if (podcastsArray == null) {
            return podcasts;
        }

        for (int i = 0; i < podcastsArray.length(); i++) {
            JSONObject item = podcastsArray.optJSONObject(i);
            if (item == null) {
                continue;
            }

            String title = item.optString("title", "").trim();
            String host = item.optString("host", "").trim();
            if (title.isEmpty() || host.isEmpty()) {
                continue;
            }

            Podcast podcast = new Podcast(
                    title,
                    host,
                    item.optInt("duration", 0),
                    item.optString("episodeTitle", ""),
                    item.optInt("episodeNumber", 0)
            );
            podcast.setRating(item.optInt("rating", 0));
            podcasts.add(podcast);
        }

        return podcasts;
    }

    private void saveAudio(Playable audio) throws IOException {
        if (audio == null) {
            return;
        }

        List<Playable> audios = loadAll();
        audios.add(audio);
        saveAll(audios);
    }

    private JSONObject loadRootJson() throws IOException {
        if (!Files.exists(historyFile)) {
            return new JSONObject();
        }

        String content = Files.readString(historyFile, StandardCharsets.UTF_8).trim();
        if (content.isEmpty()) {
            return new JSONObject();
        }

        return new JSONObject(content);
    }

    private void writeJson(String json) throws IOException {
        if (dataDirectory != null) {
            Files.createDirectories(dataDirectory);
        }

        Path tempFile = historyFile.resolveSibling(historyFile.getFileName() + ".tmp");
        Files.writeString(tempFile, json, StandardCharsets.UTF_8);
        Files.move(tempFile, historyFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private JSONObject toJson(Music music) {
        return new JSONObject()
                .put("title", music.getTitle())
                .put("artist", music.getArtist())
                .put("album", music.getAlbum())
                .put("genre", music.getGenre())
                .put("duration", music.getDuration())
                .put("rating", music.getRating());
    }

    private JSONObject toJson(Podcast podcast) {
        return new JSONObject()
                .put("title", podcast.getTitle())
                .put("host", podcast.getHost())
                .put("duration", podcast.getDuration())
                .put("rating", podcast.getRating())
                .put("episodeTitle", podcast.getEpisodeTitle())
                .put("episodeNumber", podcast.getEpisodeNumber());
    }
}
