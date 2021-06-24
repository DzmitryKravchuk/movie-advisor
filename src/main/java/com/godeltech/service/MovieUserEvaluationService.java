package com.godeltech.service;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.dto.MovieEvaluationDTO;
import com.godeltech.entity.MovieUserEvaluation;

import java.util.List;

public interface MovieUserEvaluationService {
    Integer MAX_GRADE = 5;

    void save(EvaluationRequest entity);

    MovieUserEvaluation getById(String id);

    List<MovieUserEvaluation> getAll();

    void delete(String id);

    void deleteEvaluationsByMovieId(Integer movieId);

    void update(EvaluationRequest entity, String id);

    List<MovieUserEvaluation> getAllByMovieId(Integer movieId);

    MovieUserEvaluation getByMovieIdAndByUserId(Integer id, Integer id1);

    List<MovieEvaluationDTO> getMovieEvaluationDTOs(Integer movieId);

    void deleteAll();
}
