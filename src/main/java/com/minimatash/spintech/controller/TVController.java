package com.minimatash.spintech.controller;

import com.minimatash.spintech.entity.User;
import com.minimatash.spintech.service.TVService;
import com.minimatash.spintech.service.UserService;
import com.minimatash.spintech.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tv")
public class TVController {

    private UserService userService;
    private TmdbApi tmdbApi;
    private TVService tvService;

    @RequestMapping(value = "/popular", method = POST)
    public ResponseEntity popular(@RequestParam String email,
                                  @RequestParam String password) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String popularMovies = tmdbApi.popularTVShows();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
    }

    @RequestMapping(value = "/unwatched", method = POST)
    public ResponseEntity getUnwatched(@RequestParam String email,
                                  @RequestParam String password) {
        Long userId;
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            userId = user.getId();
        }

        String popularMovies = tmdbApi.unwatchedTVShows(userId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
    }

    @RequestMapping(value = "/addWatched", method = POST)
    public ResponseEntity addWatched(@RequestParam String email,
                                     @RequestParam String password,
                                     @RequestParam Long showId) {
        Long userId;
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            userId = user.getId();
        }

        tvService.addToWatched(userId, showId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/removeWatched", method = POST)
    public ResponseEntity removeWatched(@RequestParam String email,
                                        @RequestParam String password,
                                        @RequestParam Long showId) {
        Long userId;
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            userId = user.getId();
        }

        tvService.removeFromWatched(userId, showId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }



    @Autowired
    public TVController(UserService userService, TmdbApi tmdbApi, TVService tvService) {
        this.userService = userService;
        this.tmdbApi = tmdbApi;
        this.tvService = tvService;
    }
}
