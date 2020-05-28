package com.minimatash.spintech.service;

import com.minimatash.spintech.entity.WatchedShow;

import java.util.List;

public interface TVService {
    void addToWatched(Long userId, Long showId);
    void removeFromWatched(Long userId, Long showId);
    List<WatchedShow> getWatchedShows(Long userId);
}
