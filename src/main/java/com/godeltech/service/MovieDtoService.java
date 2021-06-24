package com.godeltech.service;

import com.godeltech.dto.MovieDTO;

import java.util.List;

public interface MovieDtoService {
    MovieDTO getByIdFullInfo(Integer id);

    List<MovieDTO> getAllFullInfo();

    List<MovieDTO> getMoviesByTitleFullInfo(String favorite);

    List<MovieDTO> getMoviesByGenreFullInfo(String favorite);

    List<MovieDTO> getMoviesByCountryFullInfo(String favorite);

}
