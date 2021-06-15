package com.godeltech.service;

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

    Movie getByIdFullInfo(Integer id);

    List<Movie> getAllFullInfo();

    List<Movie> getMoviesByTitle(String favorite);

    List<Movie> getMoviesWithGenreAndCountryByGenre(String favorite);

    Set<Movie> getMoviesWithGenreAndCountryByCountry(String favorite);

    Set<Movie> getMoviesWithGenreByGenreId(Integer genreId);
}
