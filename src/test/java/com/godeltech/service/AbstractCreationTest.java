package com.godeltech.service;

import com.godeltech.dto.MovieEvaluationRequest;
import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.entity.Role;
import com.godeltech.entity.User;
import com.godeltech.exception.NotUniqueLoginException;
import com.godeltech.exception.ResourceNotFoundException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AbstractCreationTest {
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected MovieService movieService;
    @Autowired
    protected MovieDtoService movieDtoService;
    @Autowired
    protected CountryService countryService;
    @Autowired
    protected GenreService genreService;
    @Autowired
    protected MovieUserEvaluationService mueService;
    @Autowired
    protected MovieRatingService movieRatingService;

    @Before
    public void cleanBase() {
        mueService.deleteAll();
        movieService.deleteAll();
        countryService.deleteAll();
        genreService.deleteAll();
        userService.deleteAll();
        roleService.deleteAll();
    }

    protected User createNewUser(String userName) {
        final User entity = new User();
        entity.setUserName(userName);
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
        entity.setTitle("New Movie");
        entity.setDirector("New Director");
        Set<Genre> genres = Stream.of(createNewGenre("New Genre1"),
                createNewGenre("New Genre2")).collect(Collectors.toSet());
        entity.setGenres(genres);
        entity.setReleaseYear(2000);
        entity.setCountry(createNewCountry("New Country"));
        entity.setDescription("Description");
        movieService.save(entity);
        return entity;
    }

    protected Movie createNewMovieWithTitle(String title) {
        final Movie entity = new Movie();
        entity.setTitle(title);
        entity.setDirector("Director");
        Set<Genre> genres = Stream.of(createNewGenre("New Genre"),
                createNewGenre("New Genre")).collect(Collectors.toSet());
        entity.setGenres(genres);
        entity.setCountry(createNewCountry("New Country"));
        entity.setDescription("Description");
        movieService.save(entity);
        return entity;
    }

    protected MovieUserEvaluation createNewMue(int movieId, int userId, int satisfactionGrade) {
        final MovieUserEvaluation entity = new MovieUserEvaluation();
        entity.setMovieId(movieId);
        entity.setUserId(userId);
        entity.setSatisfactionGrade(satisfactionGrade);
        entity.setReview("Нечего сказать, смотри оценку");
        MovieEvaluationRequest dto = convertToRequest(entity);
        mueService.save(dto, userId);
        return entity;
    }

    private static MovieEvaluationRequest convertToRequest(final MovieUserEvaluation mue) {
        MovieEvaluationRequest request = new MovieEvaluationRequest();
        request.setReview(mue.getReview());
        request.setSatisfactionGrade(mue.getSatisfactionGrade());
        request.setMovieId(mue.getMovieId());
        return request;
    }
}
