package com.minimatash.spintech.controller;

import com.minimatash.spintech.dto.PersonDto;
import com.minimatash.spintech.entity.User;
import com.minimatash.spintech.service.UserService;
import com.minimatash.spintech.service.ActorsService;
import com.minimatash.spintech.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/person")
public class PersonController {

    private ActorsService actorsService;
    private TmdbApi tmdbApi;
    private UserService userService;

    @RequestMapping(value = "/search", method = GET)
    public List<PersonDto> personSearch(@RequestParam String query) throws URISyntaxException {
        return tmdbApi.personSearch(query);
    }

    @RequestMapping(value = "/addFavourite", method = POST)
    public ResponseEntity addToFavourite(@RequestParam String email,
                               @RequestParam String password, @RequestParam Long actorId){
        Long userId;
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            userId = user.getId();
        }

        actorsService.addToFavourite(userId ,actorId);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @RequestMapping(value = "/removeFavourite", method = POST)
    public ResponseEntity removeFromFavourite(@RequestParam String email,
                               @RequestParam String password, @RequestParam Long actorId){
        Long userId;
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            userId = user.getId();
        }

        actorsService.removeFromFavourite(userId ,actorId);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Autowired
    public PersonController(ActorsService actorsService, TmdbApi tmdbApi, UserService userService) {
        this.actorsService = actorsService;
        this.tmdbApi = tmdbApi;
        this.userService = userService;
    }
}
