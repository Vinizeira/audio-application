package com.vn.model;

public class Music extends Audio {
    private final String artist;
    private final String album;
    private final String genre;

    public Music(String title, String artist, String album, String genre, int duration) {
        super(title, duration);
        this.artist = artist;
        this.album = album;
        this.genre = genre;
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

    @Override
    public boolean hasSameIdentity(Playable other) {
        if (!(other instanceof Music music)) {
            return false;
        }

        return sameText(getTitle(), music.getTitle())
                && sameText(artist, music.getArtist())
                && sameText(album, music.getAlbum());
    }
}
