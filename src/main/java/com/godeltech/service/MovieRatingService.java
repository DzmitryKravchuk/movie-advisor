package com.godeltech.service;

import com.godeltech.entity.MovieRating;

import java.util.List;

public interface MovieRatingService {

    MovieRating getRatingByMovieId(int movieID);

    List<MovieRating> getAll ();
}
