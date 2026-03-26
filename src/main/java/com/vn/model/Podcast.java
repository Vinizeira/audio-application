package com.vn.model;

public class Podcast implements Playable {
    private String title;
    private String host;
    private int duration;
    private int rating;
    private String episodeTitle;
    private int episodeNumber;

    public Podcast(String title, String host, int duration) {
        this.title = title;
        this.host = host;
        this.duration = duration;
        this.rating = 0;
    }

    public Podcast(String title, String host, int duration, String episodeTitle, int episodeNumber) {
        this.title = title;
        this.host = host;
        this.duration = duration;
        this.rating = 0;
        this.episodeTitle = episodeTitle;
        this.episodeNumber = episodeNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public int getDuration() {
        return duration;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getHost() {
        return host;
    }

    public String getFormattedDuration() {
        int minutes = duration / 60000;
        int seconds = (duration % 60000) / 1000;
        return String.format("%d min %d sec", minutes, seconds);
    }

    @Override
    public String toString() {
        return title + " - hosted by " + host;
    }
}
