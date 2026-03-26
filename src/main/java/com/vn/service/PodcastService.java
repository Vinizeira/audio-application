package com.vn.service;

import com.vn.model.Podcast;
import com.vn.repository.PodcastRepository;

import java.io.IOException;
import java.util.List;

public class PodcastService {
    private final PodcastRepository podcastRepository = new PodcastRepository();

    public List<Podcast> searchPodcast(String search) throws IOException, InterruptedException {
        return podcastRepository.searchPodcast(search);
    }
}
