package com.godeltech.service;

import com.godeltech.dto.MovieRequest;
import com.godeltech.dto.MovieResponse;

import java.util.List;

public interface MovieDtoService {
    MovieResponse getById(Integer id);

    MovieResponse getMovieWithEvaluationsById (Integer id);

    List<MovieResponse> getAll();

    List<MovieResponse> getByTitle(String favorite);

    List<MovieResponse> getByGenre(String favorite);

    List<MovieResponse> getByCountry(String favorite);

    void saveNewMovie (MovieRequest request);

    void update(Integer id, MovieResponse res);
}
