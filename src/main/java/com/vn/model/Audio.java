package com.vn.model;

public abstract class Audio implements Playable {
    private final String title;
    private final int duration;
    private int rating;

    protected Audio(String title, int duration) {
        this.title = title;
        this.duration = duration;
        this.rating = 0;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
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

    protected boolean sameText(String left, String right) {
        return left != null && right != null && left.equalsIgnoreCase(right);
    }
}
