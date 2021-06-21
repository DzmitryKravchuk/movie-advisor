package com.godeltech;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.entity.Role;
import com.godeltech.entity.User;
import com.godeltech.exception.NotUniqueLoginException;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieService;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.service.RoleService;
import com.godeltech.service.UserService;
import com.godeltech.utils.MovieEvaluationDtoConverter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
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
    protected MovieUserEvaluationService mueService;

    protected Integer getRandomInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    @Before
    public void cleanBase() {
        mueService.deleteAll();
        movieService.deleteAll();
        countryService.deleteAll();
        genreService.deleteAll();
        userService.deleteAll();
        roleService.deleteAll();
    }

    protected User createNewUser() {
        final User entity = new User();
        entity.setUserName("User" + getRandomInt(9999));
        entity.setPassword(entity.getUserName());
        try {
            entity.setRole(roleService.getByName("ROLE_USER"));
        } catch (ResourceNotFoundException e) {
            entity.setRole(createUserRole());
        }
        try {
            userService.save(entity);
        } catch (NotUniqueLoginException e) {
            entity.setUserName(entity.getUserName() + "unique");
            userService.save(entity);
        }
        return entity;
    }

    private Role createUserRole() {
        Role userRole = new Role();
        userRole.setRoleName("ROLE_USER");
        roleService.save(userRole);
        return userRole;
    }

    protected Country createNewCountry(String countryName) {
        final Country entity = new Country();
        entity.setCountryName(countryName);
        countryService.save(entity);
        return entity;
    }

    protected Genre createNewGenre(String genreName) {
        final Genre entity = new Genre();
        entity.setGenreName(genreName);
        genreService.save(entity);
        return entity;
    }

    protected Movie createNewMovie() {
        final Movie entity = new Movie();
        entity.setTitle("New Movie" + getRandomInt(999));
        entity.setDirector("New Director" + getRandomInt(999));
        Set<Genre> genres = Stream.of(createNewGenre("New Genre" + getRandomInt(999999)),
                createNewGenre("New Genre" + getRandomInt(999999))).collect(Collectors.toSet());
        entity.setGenres(genres);
        entity.setReleaseYear(getRandomInt(20) + 1995);
        entity.setCountry(createNewCountry("New Country" + getRandomInt(9999)));
        entity.setDescription("Description" + getRandomInt(99999));
        movieService.save(entity);
        return entity;
    }

    protected Movie createNewMovieWithDirector(String director) {
        final Movie entity = new Movie();
        entity.setTitle("New Movie" + getRandomInt(999));
        entity.setDirector(director);
        Set<Genre> genres = Stream.of(createNewGenre("New Genre" + getRandomInt(999999)),
                createNewGenre("New Genre" + getRandomInt(999999))).collect(Collectors.toSet());
        entity.setGenres(genres);
        entity.setCountry(createNewCountry("New Country" + getRandomInt(9999)));
        entity.setDescription("Description" + getRandomInt(99999));
        movieService.save(entity);
        return entity;
    }

    protected Movie createNewMovieWithTitle(String title) {
        final Movie entity = new Movie();
        entity.setTitle(title);
        entity.setDirector("Director" + getRandomInt(999));
        Set<Genre> genres = Stream.of(createNewGenre("New Genre" + getRandomInt(999999)),
                createNewGenre("New Genre" + getRandomInt(999999))).collect(Collectors.toSet());
        entity.setGenres(genres);
        entity.setCountry(createNewCountry("New Country" + getRandomInt(9999)));
        entity.setDescription("Description" + getRandomInt(99999));
        movieService.save(entity);
        return entity;
    }

    protected MovieUserEvaluation createNewMueWithRandomSatisfactionGrade(int movieId, int userId) {
        final MovieUserEvaluation entity = new MovieUserEvaluation();
        entity.setMovieId(movieId);
        entity.setUserId(userId);
        entity.setSatisfactionGrade(getRandomInt(5) + 1);
        entity.setReview("Нечего сказать, смотри оценку");
        EvaluationRequest dto = MovieEvaluationDtoConverter.convertToRequest(entity, userService.getById(userId).getUserName());
        mueService.save(dto);
        return entity;
    }

    protected MovieUserEvaluation createNewMue(int movieId, int userId, int satisfactionGrade) {
        final MovieUserEvaluation entity = new MovieUserEvaluation();
        entity.setMovieId(movieId);
        entity.setUserId(userId);
        entity.setSatisfactionGrade(satisfactionGrade);
        entity.setReview("Нечего сказать, смотри оценку");
        EvaluationRequest dto = MovieEvaluationDtoConverter.convertToRequest(entity, userService.getById(userId).getUserName());
        mueService.save(dto);
        return entity;
    }
}
