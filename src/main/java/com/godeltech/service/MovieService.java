package com.godeltech.service;

import com.godeltech.dto.MovieDTO;
import com.godeltech.entity.Movie;

import java.util.List;
import java.util.Set;

public interface MovieService {
    void save(Movie entity);

    Movie getById(Integer id);

    List<Movie> getAll();

    void delete(Integer id);

    void update(Movie entity, Integer id);

    List<Movie> getMoviesByDirectorFullInfo(String favorite);

    Movie getByIdContainsGenreCountry(Integer id);

    MovieDTO getByIdFullInfo(Integer id);

    List<MovieDTO> getAllFullInfo();

    List<MovieDTO> getMoviesByTitleFullInfo(String favorite);

    List<MovieDTO> getMoviesByGenreFullInfo(String favorite);

    List<MovieDTO> getMoviesByCountryFullInfo(String favorite);

    Set<Movie> getMoviesWithGenreByGenreId(Integer genreId);

    void deleteAll();
}
