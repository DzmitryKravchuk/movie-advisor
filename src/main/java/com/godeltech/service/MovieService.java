package com.godeltech.service;

import com.godeltech.entity.Movie;

import java.util.List;

public interface MovieService {
    void save(Movie entity);

    Movie getById(Integer id);

    List<Movie> getAll();

    void delete(Integer id);

    void update(Movie entity, Integer id);

    List<Movie> getAllMoviesByDirector(String myFavoriteDirector);

    Movie getByIdContainsGenreCountry(Integer id);

    Movie getByIdFullInfo(Integer id);
}
