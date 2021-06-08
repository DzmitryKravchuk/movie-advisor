package com.godeltech.repository;

import com.godeltech.entity.MovieUserEvaluation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface MovieUserEvaluationRepository extends MongoRepository<MovieUserEvaluation, Integer> {
}
