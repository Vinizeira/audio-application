package com.vn.model;

public class Music implements Playable {
    private String title;
    private String artist;
    private String album;
    private String genre;
    private int duration; // in milliseconds
    private int rating;

    public Music(String title, String artist, String album, String genre, int duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
        this.rating = 0;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
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

    public String getFormattedDuration() {
        int minutes = duration / 60000;
        int seconds = (duration % 60000) / 1000;
        return String.format("%d min %d sec", minutes, seconds);
    }

    @Override
    public String getTitle() {
        return title;
    }
}
