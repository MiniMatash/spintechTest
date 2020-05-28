package com.minimatash.spintech.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class TvShowsDto {
    private List<Cast> cast;

    public static class Cast {

        @Getter
        private String name;

        @Getter
        private String character;

        @Getter
        private Long id;

        @Override
        public String toString() {
            return "{\"id\": " + id + ", \"name\": " + name + ", \"character\": " + (character.equals("") ? "\"\"" : character )+ "}";
        }
    }
}
