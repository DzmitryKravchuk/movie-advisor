package com.godeltech.repository;

import com.godeltech.entity.MovieUserEvaluation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieUserEvaluationRepository extends MongoRepository<MovieUserEvaluation, String> {
    @Query("{ 'movieId' : ?0}")
    List<MovieUserEvaluation> getAllByMovieId(Integer movieId);

    MovieUserEvaluation findByMovieIdAndUserId(Integer movieId, Integer userId);
}
