package com.godeltech;

import com.godeltech.entity.Movie;
import com.godeltech.exception.ServiceEntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieServiceTest extends AbstractCreationTest {
    @Test
    public void crudTest() {
        final Movie entity = createNewMovie();
        Movie entityFromBase = movieService.getById(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getTitle(), entityFromBase.getTitle());
        assertEquals(entity.getDirector(), entityFromBase.getDirector());
        movieService.delete(entity.getId());
    }

    @Test
    public void findAllMoviesByDirectorTest() {
        final String myFavoriteDirector = "myFavoriteDirector";
        for (int i = 0; i < 10; i++) {
            createNewMovieWithDirector(myFavoriteDirector);
        }
        List<Movie> movieListFromBase = movieService.getAllMoviesByDirector("favorite");
        assertEquals(movieListFromBase.size(), 10);
    }

    @Test
    public void getMovieFullInfoTest() {
        // TO DO refactor test using getFull info
        final Movie entity = createNewMovie();
        // TO DO create some more users create some more eval-s for them and new movie

        Movie entityFromBase = movieService.getByIdContainsGenreCountry(entity.getId());

        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getGenres().size(), entityFromBase.getGenres().size());
        assertEquals(entity.getCountry(), entityFromBase.getCountry());
    }

    @Test
    public void getAllMoviesFullInfoTest() {
        // TO DO some action
    }

    @Test
    public void throwExceptionTest() {
        assertThrows(ServiceEntityNotFoundException.class, () -> movieService.getByIdContainsGenreCountry(-1));
    }
}
