package com.minimatash.spintech.service.impl;

import com.minimatash.spintech.entity.FavouriteActor;
import com.minimatash.spintech.repository.PersonRepository;
import com.minimatash.spintech.service.ActorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorsServiceImpl implements ActorsService {

    private PersonRepository personRepository;

    @Override
    public void addToFavourite(Long userId, Long actorId) {
        FavouriteActor favouriteActor = new FavouriteActor();
        favouriteActor.setActorId(actorId);
        favouriteActor.setUserId(userId);
        personRepository.save(favouriteActor);
    }

    @Override
    public void removeFromFavourite(Long userId, Long actorId) {
        personRepository.delete(personRepository.getByUserIdAndActorId(userId, actorId));
    }

    public List<FavouriteActor> getFavouriteActors(Long userId){
        return personRepository.getAllByUserId(userId);
    }

    @Autowired
    public ActorsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
