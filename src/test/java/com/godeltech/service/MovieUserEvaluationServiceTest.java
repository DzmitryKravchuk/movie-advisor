package com.godeltech.service;

import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.entity.User;
import com.godeltech.exception.MovieUserEvaluationPersistenceException;
import com.godeltech.exception.ResourceNotFoundException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieUserEvaluationServiceTest extends AbstractCreationTest {

    @Test
    public void createMovieUserEvaluationTest() {
        final User user = createNewUser("User1");
        final Movie movie = createNewMovie();
        final MovieUserEvaluation entity = createNewMue(movie.getId(), user.getId(),1);
        MovieUserEvaluation entityFromBase = mueService.getByMovieIdAndByUserId(movie.getId(), user.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getReview(), entityFromBase.getReview());
        mueService.delete(entityFromBase.getId());
    }

    @Test
    public void selectAllByMovieIdTest() {
        final Movie movie = createNewMovie();
        for (int i = 0; i < 5; i++) {
            User user = createNewUser("User"+i);
            createNewMue(movie.getId(), user.getId(),i+1);
        }
        assertEquals(mueService.getAllByMovieId(movie.getId()).size(), 5);
        assertEquals(movieDtoService.getByIdFullInfo(movie.getId()).getRating(),3);
    }

    @Test
    public void throwExceptionTest() {
        assertThrows(ResourceNotFoundException.class, () -> createNewMue(-1, 1,1));
    }

    @Test
    public void throwExceptionTest1() {
        final User user = createNewUser("User1");
        final Movie movie = createNewMovie();
        createNewMue(movie.getId(), user.getId(),1);
        assertThrows(MovieUserEvaluationPersistenceException.class, () -> createNewMue(movie.getId(), user.getId(),1));
    }

    @Test
    public void throwExceptionTest2() {
        final User user = createNewUser("User1");
        final Movie movie = createNewMovie();
        assertThrows(MovieUserEvaluationPersistenceException.class, ()
                -> createNewMue(movie.getId(), user.getId(), MovieUserEvaluationService.MAX_GRADE + 1));
    }

}
