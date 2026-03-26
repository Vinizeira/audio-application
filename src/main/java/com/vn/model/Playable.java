package com.vn.model;

import com.vn.config.AppConfig;

public interface Playable {
    int getDuration();
    int getRating();
    String getTitle();
    boolean hasSameIdentity(Playable other);

    default boolean isFavorite() {
        return getRating() >= AppConfig.FAVORITE_MIN_RATING;
    }
}
