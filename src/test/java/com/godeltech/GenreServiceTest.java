package com.godeltech;

import com.godeltech.entity.Genre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenreServiceTest extends AbstractCreationTest{
    @Test
    public void crudTest() {
        final Genre entity = createNewGenre("New Genre"+getRandomInt(999999));
        Genre entityFromBase = genreService.getById(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getGenreName(), entityFromBase.getGenreName());
        genreService.delete(entity.getId());
    }
}
