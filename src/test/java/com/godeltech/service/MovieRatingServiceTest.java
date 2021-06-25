package com.godeltech.service;

import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieRating;
import com.godeltech.entity.User;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieRatingServiceTest extends AbstractCreationTest {

    @Test
    public void aggregationTest() {
        final Movie movie = createNewMovie();
        final Movie movie1 = createNewMovie();
        for (int i = 0; i < 5; i++) {
            User user = createNewUser("User" + i);
            createNewMue(movie.getId(), user.getId(), i + 1);
            createNewMue(movie1.getId(), user.getId(), 1);
        }

        List<MovieRating> resultList = movieRatingService.getAll();
        assertEquals(resultList.size(), 2);

        MovieRating ratingForMovie = movieRatingService.getRatingByMovieId(movie.getId());
        assert ratingForMovie != null;
        assertEquals(ratingForMovie.getRating(), 3);
    }
}
