package com.vn.service;

import com.vn.model.Music;
import com.vn.model.Playable;
import com.vn.model.Podcast;
import com.vn.repository.HistoryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioManagerService {
    private final HistoryRepository historyRepository = new HistoryRepository();

    public List<Playable> loadAudios() throws IOException {
        List<Playable> audios = new ArrayList<>();
        audios.addAll(historyRepository.loadMusics());
        audios.addAll(historyRepository.loadPodcasts());
        return audios;
    }

    public AudioUpdateResult addOrUpdateMusic(List<Playable> audios, Music selected, int rating) throws IOException {
        Music existingMusic = findExistingMusic(audios, selected);
        Music musicToUse = existingMusic != null ? existingMusic : selected;
        musicToUse.setRating(rating);

        if (existingMusic == null) {
            audios.add(musicToUse);
        }

        saveHistory(audios);
        return new AudioUpdateResult(musicToUse, existingMusic == null);
    }

    public AudioUpdateResult addOrUpdatePodcast(List<Playable> audios, Podcast selected, int rating) throws IOException {
        Podcast existingPodcast = findExistingPodcast(audios, selected);
        Podcast podcastToUse = existingPodcast != null ? existingPodcast : selected;
        podcastToUse.setRating(rating);

        if (existingPodcast == null) {
            audios.add(podcastToUse);
        }

        saveHistory(audios);
        return new AudioUpdateResult(podcastToUse, existingPodcast == null);
    }

    public void saveHistory(List<Playable> audios) throws IOException {
        historyRepository.saveAll(audios);
    }

    private Music findExistingMusic(List<Playable> audios, Music selected) {
        for (Playable audio : audios) {
            if (audio instanceof Music music
                    && music.getTitle().equalsIgnoreCase(selected.getTitle())
                    && music.getArtist().equalsIgnoreCase(selected.getArtist())
                    && music.getAlbum().equalsIgnoreCase(selected.getAlbum())) {
                return music;
            }
        }
        return null;
    }

    private Podcast findExistingPodcast(List<Playable> audios, Podcast selected) {
        for (Playable audio : audios) {
            if (audio instanceof Podcast podcast
                    && podcast.getTitle().equalsIgnoreCase(selected.getTitle())
                    && podcast.getHost().equalsIgnoreCase(selected.getHost())
                    && podcast.getEpisodeNumber() == selected.getEpisodeNumber()) {
                return podcast;
            }
        }
        return null;
    }
}
