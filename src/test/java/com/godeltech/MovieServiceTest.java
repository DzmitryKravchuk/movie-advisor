package com.godeltech;

import com.godeltech.dto.MovieDTO;
import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.User;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.utils.MovieDtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
    public void findAllMoviesByDirectorTest() {
        final String myFavoriteDirector = "myFavoriteDirector";
        for (int i = 0; i < 3; i++) {
            Movie movie = createNewMovieWithDirector(myFavoriteDirector);
            createNewMueWithRandomSatisfactionGrade(movie.getId(), createNewUser().getId());
            createNewMueWithRandomSatisfactionGrade(movie.getId(), createNewUser().getId());
        }
        List<Movie> movieListFromBase = movieService.getMoviesByDirectorFullInfo("favorite");
        assertEquals(movieListFromBase.size(), 3);
        assertEquals(movieListFromBase.get(0).getMovieEvaluations().size(), 2);
        assertNotNull(movieListFromBase.get(0).getCountry());
        assert (movieListFromBase.get(0).getGenres().size() >= 1);
        assert (movieListFromBase.get(0).getAvgSatisfactionGrade() >= 0);
        assert (movieListFromBase.get(0).getAvgSatisfactionGrade() <= 5);
    }

    @Test
    public void findMoviesByTitleTest() {
        final String myFavoriteTitle = "myFavoriteTitle";
        for (int i = 0; i < 10; i++) {
            Movie movie = createNewMovieWithTitle(myFavoriteTitle);
            createNewMueWithRandomSatisfactionGrade(movie.getId(), createNewUser().getId());
            createNewMueWithRandomSatisfactionGrade(movie.getId(), createNewUser().getId());
        }
        List<Movie> movieListFromBase = movieService.getMoviesByTitleFullInfo("favorite");
        assertEquals(movieListFromBase.size(), 10);
        assertEquals(movieListFromBase.get(0).getMovieEvaluations().size(), 2);
        assertNotNull(movieListFromBase.get(0).getCountry());
        assert (movieListFromBase.get(0).getGenres().size() >= 1);
        assert (movieListFromBase.get(0).getAvgSatisfactionGrade() >= 0);
        assert (movieListFromBase.get(0).getAvgSatisfactionGrade() <= 5);
    }

    @Test
    public void findMoviesByGenreTest1() {
        Genre genre = createNewGenre("New Genre" + getRandomInt(999999));
        final String myFavorite = "Триллер";
        genre.setGenreName(myFavorite);
        genreService.update(genre, genre.getId());
        for (int i = 0; i < 10; i++) {
            Movie movie = createNewMovie();
            Set<Genre> genres = movie.getGenres();
            genres.add(genre);
            movie.setGenres(genres);
            movieService.update(movie, movie.getId());
        }
        Set<Movie> movieListFromBase = movieService.getMoviesWithGenreByGenreId(genre.getId());
        assertEquals(movieListFromBase.size(), 10);
    }

    @Test
    public void findMoviesByGenreTest2() {
        final String myFavorite = "XXX";
        Genre genre = createNewGenre(myFavorite);
        for (int i = 0; i < 10; i++) {
            Movie movie = createNewMovie();
            Set<Genre> genres = movie.getGenres();
            genres.add(genre);
            movie.setGenres(genres);
            movieService.update(movie, movie.getId());
            createNewMueWithRandomSatisfactionGrade(movie.getId(), createNewUser().getId());
            createNewMueWithRandomSatisfactionGrade(movie.getId(), createNewUser().getId());
        }
        List<Movie> movieListFromBase = movieService.getMoviesByGenreFullInfo(myFavorite);
        assertEquals(movieListFromBase.size(), 10);
        assertEquals(movieListFromBase.iterator().next().getMovieEvaluations().size(), 2);
        assertNotNull(movieListFromBase.iterator().next().getCountry());
        assert (movieListFromBase.iterator().next().getGenres().size() >= 1);
        assert (movieListFromBase.iterator().next().getAvgSatisfactionGrade() >= 0);
        assert (movieListFromBase.iterator().next().getAvgSatisfactionGrade() <= 5);
    }

     @Test
    public void findMoviesByCountryTest() {
        final String myFavorite = "Беларусь";
        Country country = createNewCountry(myFavorite);
        for (int i = 0; i < 10; i++) {
            Movie movie = createNewMovie();
            movie.setCountry(country);
            movieService.update(movie, movie.getId());
            createNewMueWithRandomSatisfactionGrade(movie.getId(), createNewUser().getId());
            createNewMueWithRandomSatisfactionGrade(movie.getId(), createNewUser().getId());
        }
        List<Movie> movieListFromBase = movieService.getMoviesByCountryFullInfo(myFavorite);
        assertEquals(movieListFromBase.size(), 10);
        assertEquals(movieListFromBase.get(0).getMovieEvaluations().size(), 2);
        assertNotNull(movieListFromBase.get(0).getCountry());
        assert (movieListFromBase.get(0).getGenres().size() >= 1);
        assert (movieListFromBase.get(0).getAvgSatisfactionGrade() >= 0);
        assert (movieListFromBase.get(0).getAvgSatisfactionGrade() <= 5);
    }

    @Test
    public void getMovieFullInfoTest() {
        final Movie movie = createNewMovie();
        final User user1 = createNewUser();
        final User user2 = createNewUser();
        createNewMue(movie.getId(), user1.getId(), 5);
        createNewMue(movie.getId(), user2.getId(), 2);

        MovieDTO entityFromBase = movieService.getByIdFullInfo(movie.getId());

        assertNotNull(entityFromBase.getId());
        assertEquals(movie.getMovieEvaluations().size(), entityFromBase.getEvaluations().size());
        assertEquals(entityFromBase.getRating(), 3);
    }

    @Test
    public void getAllMoviesFullInfoTest() {
        final int initCount = movieService.getAll().size();
        final Movie movie1 = createNewMovie();
        final Movie movie2 = createNewMovie();
        final Movie movie3 = createNewMovie();
        final User user1 = createNewUser();
        final User user2 = createNewUser();
        createNewMueWithRandomSatisfactionGrade(movie1.getId(), user1.getId());
        createNewMueWithRandomSatisfactionGrade(movie1.getId(), user2.getId());

        createNewMueWithRandomSatisfactionGrade(movie2.getId(), user1.getId());
        createNewMueWithRandomSatisfactionGrade(movie2.getId(), user2.getId());

        createNewMueWithRandomSatisfactionGrade(movie3.getId(), user1.getId());
        createNewMueWithRandomSatisfactionGrade(movie3.getId(), user2.getId());

        List<MovieDTO> entitiesFromBase = movieService.getAllFullInfo();

        assert (entitiesFromBase.contains(MovieDtoConverter.convertToDTO(movie1)));
        assertEquals(entitiesFromBase.size(), initCount + 3);
        assert (entitiesFromBase.get(initCount + 2).getRating() > 0);
    }

    @Test
    public void throwExceptionTest() {
        assertThrows(ResourceNotFoundException.class, () -> movieService.getByIdContainsGenreCountry(-1));
    }
}
