package com.vn.service;

import com.vn.model.Playable;

import java.util.ArrayList;
import java.util.List;

public class FavoritesService {
    public List<Playable> getFavorites(List<Playable> audios) {
        List<Playable> favorites = new ArrayList<>();
        for (Playable audio : audios) {
            if (audio.getRating() >= 3) {
                favorites.add(audio);
            }
        }
        return favorites;
    }
}
