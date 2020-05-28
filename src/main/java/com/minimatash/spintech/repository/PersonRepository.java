package com.minimatash.spintech.repository;

import com.minimatash.spintech.entity.FavouriteActor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<FavouriteActor, Long> {
    List<FavouriteActor> getAllByUserId(Long userId);

    FavouriteActor getByUserIdAndActorId(Long userId, Long showId);
}
