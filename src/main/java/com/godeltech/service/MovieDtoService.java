package com.godeltech.service;

import com.godeltech.dto.MovieDTO;

import java.util.List;

public interface MovieDtoService {
    MovieDTO getById(Integer id);

    List<MovieDTO> getAll();

    List<MovieDTO> getByTitle(String favorite);

    List<MovieDTO> getByGenre(String favorite);

    List<MovieDTO> getByCountry(String favorite);

}
