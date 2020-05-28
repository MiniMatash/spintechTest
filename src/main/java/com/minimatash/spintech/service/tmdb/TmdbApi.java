package com.minimatash.spintech.service.tmdb;

import com.minimatash.spintech.dto.PersonDto;

import java.net.URISyntaxException;
import java.util.List;

public interface TmdbApi {
    List<PersonDto> personSearch(String query) throws URISyntaxException;
    String popularTVShows();
    String unwatchedTVShows(Long userId);
}
