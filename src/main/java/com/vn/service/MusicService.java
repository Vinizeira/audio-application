package com.vn.service;

import com.vn.model.Music;
import com.vn.repository.MusicRepository;

import java.io.IOException;
import java.util.List;

public class MusicService {
    private final MusicRepository musicRepository;

    public MusicService() {
        this(new MusicRepository());
    }

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public List<Music> searchMusic(String search) throws IOException, InterruptedException {
        return musicRepository.searchMusic(search);
    }
}
