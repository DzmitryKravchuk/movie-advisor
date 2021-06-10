package com.godeltech;

import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
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
        entity.setGenreName("New Genre"+getRandomInt(99));
        genreService.save(entity);
        return entity;
    }
}
