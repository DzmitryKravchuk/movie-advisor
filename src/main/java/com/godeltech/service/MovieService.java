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

    Movie getByIdContainsGenreCountry(Integer id);

    List<Movie> getAllWithCountryAndGenre();

    List<Movie> getAllByTitleFullInfo(String favorite);

    List<Movie> getAllByGenreFullInfo(String favorite);

    Set<Movie> getAllByCountryFullInfo(String favorite);

    Set<Movie> getMoviesWithGenreByGenreId(Integer genreId);

    void deleteAll();
}
