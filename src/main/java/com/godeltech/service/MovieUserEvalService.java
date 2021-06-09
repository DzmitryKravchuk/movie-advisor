package com.godeltech.service;

import com.godeltech.entity.MovieUserEvaluation;

import java.util.List;

public interface MovieUserEvalService {

    void save(MovieUserEvaluation entity);

    MovieUserEvaluation getById(String id);

    List<MovieUserEvaluation> getAll();

    void delete(String id);

    void update(MovieUserEvaluation entity, String id);
}
