package com.minimatash.spintech.repository;

import com.minimatash.spintech.entity.WatchedShow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WatchedShowRepository extends CrudRepository<WatchedShow, Long> {
    List<WatchedShow> getAllByUserId(Long userId);

    WatchedShow getByUserIdAndShowId(Long userId, Long showId);
}
