package com.minimatash.spintech.service.tmdb.impl;

import com.minimatash.spintech.dto.PersonDto;
import com.minimatash.spintech.dto.PersonSearchResult;
import com.minimatash.spintech.dto.TvShowsDto;
import com.minimatash.spintech.entity.FavouriteActor;
import com.minimatash.spintech.entity.WatchedShow;
import com.minimatash.spintech.service.ActorsService;
import com.minimatash.spintech.service.TVService;
import com.minimatash.spintech.service.tmdb.TmdbApi;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class TmdbApiImpl implements TmdbApi {
    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    private ActorsService actorsService;
    private TVService tvService;


    private final Logger logger = LogManager.getLogger(this.getClass());

    public String popularTVShows() throws IllegalArgumentException {
        try {
            String url = getTmdbUrl("/tv/popular", new HashMap<>());

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            return response.getBody();
        } catch (URISyntaxException e) {
            logger.error("Couldn't get popular tv shows");
        }
        return null;
    }

    @Override
    public String unwatchedTVShows(Long userId) {
        try {
            List<FavouriteActor> favouriteActors = actorsService.getFavouriteActors(userId);
            List<WatchedShow> watchedShows = tvService.getWatchedShows(userId);
            StringBuilder result = new StringBuilder();
            result.append("{\"cast\": [");
            for(FavouriteActor actor: favouriteActors){
                TvShowsDto tvShows = getPerson(actor.getActorId());
                tvShows.getCast().forEach(show ->{
                    AtomicBoolean check = new AtomicBoolean(true);
                    watchedShows.forEach(watchedShow -> {
                        if(watchedShow.getShowId().equals(show.getId())){
                            check.set(false);
                        }
                    });
                    if(check.get()){
                        result.append(show.toString())
                        .append(",");
                    }
                });
                result.deleteCharAt(result.length()-1);
            }
            result.append("]}");
            return result.toString();
        } catch (URISyntaxException e) {
            logger.error("Couldn't get popular tv shows");
        }
        return null;
    }

    @Override
    public List<PersonDto> personSearch(String query) throws URISyntaxException {
        Map<String, String> params = new HashMap<>();
        params.put("query", query);
        String url = getTmdbUrl("/search/person?", params);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PersonSearchResult> response
                = restTemplate.getForEntity(url, PersonSearchResult.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }
        PersonSearchResult personSearchResult = response.getBody();
        if (personSearchResult != null) {
            return personSearchResult.getResults();
        } else {
            return new ArrayList<>();
        }
    }

    public TvShowsDto getPerson(Long actorId) throws URISyntaxException {
        String url = getTmdbUrl("/person/" + actorId + "/tv_credits", new HashMap<>());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TvShowsDto> response
                = restTemplate.getForEntity(url, TvShowsDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return response.getBody();
    }

    private String getTmdbUrl(String tmdbItem, Map<String, String> params) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(tmdbApiBaseUrl + tmdbItem);
        uriBuilder.addParameter("language", tmdbLanguage);
        uriBuilder.addParameter("api_key", tmdbApiKey);
        params.forEach(uriBuilder::addParameter);
        return uriBuilder.build().toString();
    }

    @Autowired
    public TmdbApiImpl(ActorsService actorsService, TVService tvService) {
        this.actorsService = actorsService;
        this.tvService = tvService;
    }
}
