package com.godeltech;

import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.User;
import com.godeltech.service.MovieService;
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

    protected Integer getRandomInt() {
        return RANDOM.nextInt(99999);
    }

    protected User createNewUser() {
        final User entity = new User();
        entity.setUserName("User" + getRandomInt());
        entity.setPassword(entity.getUserName());
        entity.setRole(roleService.getById(1));
        userService.save(entity);
        return entity;
    }

    protected Country createNewCountry() {
        final Country entity = new Country();
        //TO DO some action
        return entity;
    }

    protected Genre createNewGenre() {
        final Genre entity = new Genre();
        //TO DO some action
        return entity;
    }

    protected Movie createNewMovie() {
        final Movie entity = new Movie();
        //TO DO some action
        return entity;
    }

}
