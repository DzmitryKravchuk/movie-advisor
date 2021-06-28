package com.godeltech.service;

import com.godeltech.dto.MovieEvaluationRequest;
import com.godeltech.dto.MovieEvaluationResponse;
import com.godeltech.entity.MovieUserEvaluation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MovieUserEvaluationService {
    Integer MAX_GRADE = 5;

    void save(MovieEvaluationRequest entity, Integer userId);

    MovieUserEvaluation getById(String id);

    List<MovieUserEvaluation> getAll();

    void delete(String id);

    void deleteEvaluationsByMovieId(Integer movieId);

    void update(MovieEvaluationRequest entity, String id, HttpServletRequest servletRequest);

    List<MovieUserEvaluation> getAllByMovieId(Integer movieId);

    MovieUserEvaluation getByMovieIdAndByUserId(Integer id, Integer id1);

    List<MovieEvaluationResponse> getMovieEvaluationDTOs(Integer movieId);

    void deleteAll();

    void saveWithToken(MovieEvaluationRequest request, HttpServletRequest servletRequest);
}
