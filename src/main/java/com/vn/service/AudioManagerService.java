package com.vn.service;

import com.vn.model.Audio;
import com.vn.model.Music;
import com.vn.model.Playable;
import com.vn.model.Podcast;
import com.vn.repository.HistoryRepository;

import java.io.IOException;
import java.util.List;

public class AudioManagerService {
    private final HistoryRepository historyRepository;

    public AudioManagerService() {
        this(new HistoryRepository());
    }

    public AudioManagerService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<Playable> loadAudios() throws IOException {
        return historyRepository.loadAll();
    }

    public AudioUpdateResult addOrUpdateMusic(List<Playable> audios, Music selected, int rating) throws IOException {
        return addOrUpdateAudio(audios, selected, rating);
    }

    public AudioUpdateResult addOrUpdatePodcast(List<Playable> audios, Podcast selected, int rating) throws IOException {
        return addOrUpdateAudio(audios, selected, rating);
    }

    public <T extends Audio> AudioUpdateResult addOrUpdateAudio(List<Playable> audios, T selected, int rating) throws IOException {
        T existingAudio = findExistingAudio(audios, selected);
        T audioToUse = existingAudio != null ? existingAudio : selected;
        audioToUse.setRating(rating);

        if (existingAudio == null) {
            audios.add(audioToUse);
        }

        saveHistory(audios);
        return new AudioUpdateResult(audioToUse, existingAudio == null);
    }

    public void saveHistory(List<Playable> audios) throws IOException {
        historyRepository.saveAll(audios);
    }

    private <T extends Audio> T findExistingAudio(List<Playable> audios, T selected) {
        for (Playable audio : audios) {
            if (selected.getClass().isInstance(audio) && audio.hasSameIdentity(selected)) {
                @SuppressWarnings("unchecked")
                T existingAudio = (T) audio;
                return existingAudio;
            }
        }
        return null;
    }
}
