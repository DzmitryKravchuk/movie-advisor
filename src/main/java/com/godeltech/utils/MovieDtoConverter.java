package com.godeltech.utils;

import com.godeltech.dto.MovieDTO;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;

import java.util.stream.Collectors;

public class MovieDtoConverter {

    public static MovieDTO convertToDTO(final Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setCountry(movie.getCountry().getCountryName());
        dto.setRating(movie.getAvgSatisfactionGrade());
        dto.setGenres(movie.getGenres().stream().map(Genre::getGenreName)
                .collect(Collectors.toSet()));
        dto.setDirector(movie.getDirector());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setReleaseYear(movie.getReleaseYear());

        return dto;
    }
}
