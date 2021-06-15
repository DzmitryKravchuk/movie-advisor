package com.godeltech;

import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.entity.User;
import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieService;
import com.godeltech.service.MovieUserEvalService;
import com.godeltech.service.RoleService;
import com.godeltech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@SpringBootTest
public class AbstractCreationTest {
    protected static final Random RANDOM = new Random();
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected MovieService movieService;
    @Autowired
    protected CountryService countryService;
    @Autowired
    protected GenreService genreService;
    @Autowired
    protected MovieUserEvalService mueService;

    protected Integer getRandomInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    protected User createNewUser() {
        final User entity = new User();
        entity.setUserName("User" + getRandomInt(9999));
        entity.setPassword(entity.getUserName());
        entity.setRole(roleService.getById(1));
        userService.save(entity);
        return entity;
    }

    protected Country createNewCountry() {
        final Country entity = new Country();
        entity.setCountryName("New Country"+ getRandomInt(9999));
        countryService.save(entity);
        return entity;
    }

    protected Genre createNewGenre() {
        final Genre entity = new Genre();
        entity.setGenreName("New Genre"+getRandomInt(999999));
        genreService.save(entity);
        return entity;
    }

    protected Movie createNewMovie() {
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

    protected Movie createNewMovieWithDirector(String director) {
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

    protected Movie createNewMovieWithTitle(String title) {
        final Movie entity = new Movie();
        entity.setTitle(title);
        entity.setDirector("Director"+getRandomInt(999));
        Set<Genre> genres = Stream.of(createNewGenre(), createNewGenre()).collect(Collectors.toSet());
        entity.setGenres(genres);
        entity.setCountry(createNewCountry());
        entity.setDescription("Description" + getRandomInt(99999));
        movieService.save(entity);
        return entity;
    }

    protected MovieUserEvaluation createNewMueWithRandomSatisfactionGrade(int movieId, int UserId) {
        final MovieUserEvaluation entity = new MovieUserEvaluation();
        entity.setMovieId(movieId);
        entity.setUserId(UserId);
        entity.setSatisfactionGrade(getRandomInt(5) + 1);
        entity.setReview("Нечего сказать, смотри оценку");
        mueService.save(entity);
        return entity;
    }

    protected MovieUserEvaluation createNewMue(int movieId, int userId, int satisfactionGrade) {
        final MovieUserEvaluation entity = new MovieUserEvaluation();
        entity.setMovieId(movieId);
        entity.setUserId(userId);
        entity.setSatisfactionGrade(satisfactionGrade);
        entity.setReview("Нечего сказать, смотри оценку");
        mueService.save(entity);
        return entity;
    }
}
