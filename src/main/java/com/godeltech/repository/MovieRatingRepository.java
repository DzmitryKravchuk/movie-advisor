package com.godeltech.repository;

import com.godeltech.entity.MovieRating;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
@RequiredArgsConstructor
public class MovieRatingRepository {
   private final MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.collection-name}")
    private String collectionName;

    public Optional<MovieRating> getRatingByMovieId(int movieId) {
        MatchOperation matchByMovieId = match(Criteria.where("movieId").is(movieId));
        Aggregation aggregation1 = newAggregation(matchByMovieId, getGroupOperation());
        AggregationResults<MovieRating> result = mongoTemplate.aggregate(aggregation1, collectionName, MovieRating.class);
       return Optional.ofNullable(result.getUniqueMappedResult());
    }

    public List<MovieRating> getAll() {
        SortOperation sortByMovieId = Aggregation.sort(Sort.by(Sort.Direction.ASC, "movieId"));
        Aggregation aggregation = newAggregation(getGroupOperation(), sortByMovieId);
        AggregationResults<MovieRating> result = mongoTemplate.aggregate(aggregation, collectionName, MovieRating.class);
        return  result.getMappedResults();
    }

    private GroupOperation getGroupOperation() {
        return Aggregation.group("movieId").avg("satisfactionGrade").as("rating");
    }
}
