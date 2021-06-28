package com.godeltech.service;

import com.godeltech.dto.MovieResponse;
import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.User;
import com.godeltech.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
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
    public void findMoviesByTitleTest() {
        final String myFavoriteTitle = "myFavoriteTitle";
        for (int i = 0; i < 10; i++) {
            Movie movie = createNewMovieWithTitle(myFavoriteTitle);
            createNewMue(movie.getId(), createNewUser("FirstUser" + i).getId(), 1);
            createNewMue(movie.getId(), createNewUser("SecondUser" + i).getId(), 2);
        }
        List<MovieResponse> movieListFromBase = movieDtoService.getByTitle("favorite");
        assertEquals(movieListFromBase.size(), 10);
        assertEquals(mueService.getMovieEvaluationDTOs(movieListFromBase.get(0).getId()).size(), 2);
        assertNotNull(movieListFromBase.get(0).getCountry());
        assert (movieListFromBase.get(0).getGenres().size() >= 1);
        assert (movieListFromBase.get(0).getRating() >= 0);
        assert (movieListFromBase.get(0).getRating() <= 5);
    }

    @Test
    public void findMoviesByGenreTest() {
        final String myFavorite = "XXX";
        Genre genre = createNewGenre(myFavorite);
        for (int i = 0; i < 10; i++) {
            Movie movie = createNewMovie();
            Set<Genre> genres = movie.getGenres();
            genres.add(genre);
            movie.setGenres(genres);
            movieService.update(movie, movie.getId());
            createNewMue(movie.getId(), createNewUser("FirstUser" + i).getId(), 1);
            createNewMue(movie.getId(), createNewUser("SecondUser" + i).getId(), 2);
        }
        List<MovieResponse> movieListFromBase = movieDtoService.getByGenre(myFavorite);
        assertEquals(movieListFromBase.size(), 10);
        assertEquals(mueService.getMovieEvaluationDTOs(movieListFromBase.get(0).getId()).size(), 2);
        assertNotNull(movieListFromBase.iterator().next().getCountry());
        assert (movieListFromBase.iterator().next().getGenres().size() >= 1);
        assert (movieListFromBase.iterator().next().getRating() >= 0);
        assert (movieListFromBase.iterator().next().getRating() <= 5);
    }

    @Test
    public void findMoviesByCountryTest() {
        final String myFavorite = "Беларусь";
        Country country = createNewCountry(myFavorite);
        for (int i = 0; i < 10; i++) {
            Movie movie = createNewMovie();
            movie.setCountry(country);
            movieService.update(movie, movie.getId());
            createNewMue(movie.getId(), createNewUser("FirstUser" + i).getId(), 1);
            createNewMue(movie.getId(), createNewUser("SecondUser" + i).getId(), 2);
        }
        List<MovieResponse> movieListFromBase = movieDtoService.getByCountry(myFavorite);
        assertEquals(movieListFromBase.size(), 10);
        assertEquals(mueService.getMovieEvaluationDTOs(movieListFromBase.get(0).getId()).size(), 2);
        assertNotNull(movieListFromBase.get(0).getCountry());
        assert (movieListFromBase.get(0).getGenres().size() >= 1);
        assert (movieListFromBase.get(0).getRating() >= 0);
        assert (movieListFromBase.get(0).getRating() <= 5);
    }

    @Test
    public void getMovieFullInfoTest() {
        final Movie movie = createNewMovie();
        final User user1 = createNewUser("User1");
        final User user2 = createNewUser("User2");
        createNewMue(movie.getId(), user1.getId(), 5);
        createNewMue(movie.getId(), user2.getId(), 2);

        MovieResponse entityFromBase = movieDtoService.getById(movie.getId());

        assertNotNull(entityFromBase.getId());
        assertEquals(mueService.getAllByMovieId(movie.getId()).size(), mueService.getMovieEvaluationDTOs(entityFromBase.getId()).size());
        assertEquals(entityFromBase.getRating(), 3);
    }

    @Test
    public void getAllMoviesFullInfoTest() {
        final int initCount = movieService.getAll().size();
        final Movie movie1 = createNewMovie();
        final Movie movie2 = createNewMovie();
        final Movie movie3 = createNewMovie();
        final User user1 = createNewUser("User1");
        final User user2 = createNewUser("User2");
        createNewMue(movie1.getId(), user1.getId(), 1);
        createNewMue(movie1.getId(), user2.getId(), 2);

        createNewMue(movie2.getId(), user1.getId(), 1);
        createNewMue(movie2.getId(), user2.getId(), 2);

        createNewMue(movie3.getId(), user1.getId(), 1);
        createNewMue(movie3.getId(), user2.getId(), 2);

        MovieResponse movieFromBase = movieDtoService.getById(movie1.getId());

        List<MovieResponse> entitiesFromBase = movieDtoService.getAll();

        assert (entitiesFromBase.contains(movieFromBase));
        assertEquals(entitiesFromBase.size(), initCount + 3);
        assert (entitiesFromBase.get(initCount + 2).getRating() > 0);
    }

    @Test
    public void throwExceptionTest() {
        assertThrows(ResourceNotFoundException.class, () -> movieService.getByIdContainsGenreCountry(-1));
    }
}
