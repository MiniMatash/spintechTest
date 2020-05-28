package com.minimatash.spintech.service;


import com.minimatash.spintech.entity.FavouriteActor;

import java.util.List;

public interface ActorsService {
    void addToFavourite(Long userId, Long actorId);
    void removeFromFavourite(Long userId, Long actorId);
    List<FavouriteActor> getFavouriteActors(Long userId);
}
