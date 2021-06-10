package com.godeltech.repository;

import com.godeltech.entity.MovieUserEvaluation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;

public interface MovieUserEvaluationRepository extends MongoRepository<MovieUserEvaluation, String> {
    @Query("{ 'movieId' : ?0}")
    Set<MovieUserEvaluation> getAllByMovieId(Integer movieId);
}
