package com.godeltech.service.impl;

import com.godeltech.entity.MovieRating;
import com.godeltech.repository.MovieRatingRepository;
import com.godeltech.service.MovieRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieRatingServiceImpl implements MovieRatingService {
    private final MovieRatingRepository repository;

    @Override
    public MovieRating getRatingByMovieId(int movieId) {
        log.info("getRatingForMovie {}", movieId);
        return repository.getRatingByMovieId(movieId)
                .orElseGet(() -> new MovieRating(movieId, 0));
    }

    @Override
    public List<MovieRating> getAll() {
        return repository.getAll();
    }
}
