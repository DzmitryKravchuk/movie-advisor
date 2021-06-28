package com.godeltech.utils;

import com.godeltech.dto.MovieRequest;
import com.godeltech.dto.MovieResponse;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;

import java.util.stream.Collectors;

public class MovieDtoConverter {

    public static MovieResponse convertToResponse(Movie movie) {
        MovieResponse dto = new MovieResponse();
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

    public static Movie convertFromResponse(MovieResponse dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setDirector(dto.getDirector());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDescription(dto.getDescription());

        return movie;
    }

    public static Movie convertFromRequest(MovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setDirector(request.getDirector());
        movie.setDescription(request.getDescription());

        return movie;
    }
}
