package com.godeltech.repository;

import com.godeltech.entity.MovieUserEvaluation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieUserEvaluationRepository extends MongoRepository<MovieUserEvaluation, String> {
}
