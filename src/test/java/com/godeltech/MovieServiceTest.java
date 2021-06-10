package com.godeltech;

import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.exception.ServiceEntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            createNewMovie(myFavoriteDirector);
        }
        List<Movie> movieListFromBase = movieService.getAllMoviesByDirector("favorite");
        assertEquals(movieListFromBase.size(), 10);
    }

    @Test
    public void getMovieGenreCountryTest() {
        final Movie entity = createNewMovie();
        Movie entityFromBase = movieService.getByIdContainsGenreCountry(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getGenres().size(), entityFromBase.getGenres().size());
        assertEquals(entity.getCountry(), entityFromBase.getCountry());
    }

    @Test
    public void throwExceptionTest() {
        assertThrows(ServiceEntityNotFoundException.class, () -> {
            Movie entityFromBase = movieService.getByIdContainsGenreCountry(-1);
        });
    }

    private Movie createNewMovie() {
        final Movie entity = new Movie();
        entity.setTitle("New Movie" + getRandomInt(999));
        entity.setDirector("New Director" + getRandomInt(999));
        Set<Genre> genres = Stream.of(createNewGenre(), createNewGenre()).collect(Collectors.toSet());
        entity.setGenres(genres);
        entity.setCountry(createNewCountry());
        entity.setDescription("Description" + getRandomInt(99999));
        movieService.save(entity);
        return entity;
    }

    private Movie createNewMovie(String director) {
        final Movie entity = new Movie();
        entity.setTitle("New Movie" + getRandomInt(999));
        entity.setDirector(director);
        Set<Genre> genres = Stream.of(createNewGenre(), createNewGenre()).collect(Collectors.toSet());
        entity.setGenres(genres);
        entity.setCountry(createNewCountry());
        entity.setDescription("Description" + getRandomInt(99999));
        movieService.save(entity);
        return entity;
    }
}
