package com.vn.model;

public class Podcast extends Audio {
    private final String host;
    private final String episodeTitle;
    private final int episodeNumber;

    public Podcast(String title, String host, int duration) {
        super(title, duration);
        this.host = host;
        this.episodeTitle = "";
        this.episodeNumber = 0;
    }

    public Podcast(String title, String host, int duration, String episodeTitle, int episodeNumber) {
        super(title, duration);
        this.host = host;
        this.episodeTitle = episodeTitle == null ? "" : episodeTitle;
        this.episodeNumber = episodeNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public String getHost() {
        return host;
    }

    @Override
    public String toString() {
        return getTitle() + " - hosted by " + host;
    }

    @Override
    public boolean hasSameIdentity(Playable other) {
        if (!(other instanceof Podcast podcast)) {
            return false;
        }

        return sameText(getTitle(), podcast.getTitle())
                && sameText(host, podcast.getHost())
                && episodeNumber == podcast.getEpisodeNumber();
    }
}
