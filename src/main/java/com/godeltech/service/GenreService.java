package com.godeltech.service;

import com.godeltech.entity.Genre;

import java.util.List;

public interface GenreService {
    void save(Genre entity);

    Genre getById(Integer id);

    List<Genre> getAll();

    void delete(Integer id);

    void update(Genre entity, Integer id);
}
