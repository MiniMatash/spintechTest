package com.minimatash.spintech.service.impl;

import com.minimatash.spintech.entity.FavouriteActor;
import com.minimatash.spintech.entity.WatchedShow;
import com.minimatash.spintech.repository.WatchedShowRepository;
import com.minimatash.spintech.service.TVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TVServiceImpl implements TVService {

    private WatchedShowRepository watchedShowRepository;

    @Override
    public void addToWatched(Long userId, Long showId) {
        WatchedShow watchedShow = new WatchedShow();
        watchedShow.setShowId(showId);
        watchedShow.setUserId(userId);
        watchedShowRepository.save(watchedShow);
    }

    @Override
    public void removeFromWatched(Long userId, Long showId) {
        watchedShowRepository.delete(watchedShowRepository.getByUserIdAndShowId(userId, showId));
    }

    public List<WatchedShow> getWatchedShows(Long userId){
        return watchedShowRepository.getAllByUserId(userId);
    }

    @Autowired
    public TVServiceImpl(WatchedShowRepository watchedShowRepository) {
        this.watchedShowRepository = watchedShowRepository;
    }
}
