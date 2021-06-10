package com.godeltech;

import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovieServiceTest extends AbstractCreationTest{
    @Test
    public void crudTest() {
        final Movie entity = createNewMovie();
        Movie entityFromBase = movieService.getById(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getTitle(), entityFromBase.getTitle());
        assertEquals(entity.getDirector(), entityFromBase.getDirector());
        movieService.delete(entity.getId());
    }

    private Movie createNewMovie() {
        final Movie entity = new Movie();
        entity.setTitle("New Movie"+getRandomInt(999));
        entity.setDirector("New Director"+getRandomInt(999));
        List<Genre> genreList = Arrays.asList(new Genre[]{createNewGenre(), createNewGenre()});
        entity.setGenreList(genreList);
        entity.setCountry(createNewCountry());
        entity.setDescription("Description"+getRandomInt(99999));
        movieService.save(entity);
        return entity;
    }
}
