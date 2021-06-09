package com.godeltech;

import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.service.MovieUserEvalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class MongoTest {
    @Autowired
    protected MovieUserEvalService mueService;

    @Test
    public void createMovieUserEvaluationTest() {
        final MovieUserEvaluation entity = createNewMue();
        MovieUserEvaluation entityFromBase = mueService.getById(entity.getId());
        log.info("createMovieUserEvaluationTest() entityFromBase {}", entityFromBase);
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getReview(), entityFromBase.getReview());
        mueService.delete(entity.getId());
    }

    private MovieUserEvaluation createNewMue() {
        final MovieUserEvaluation entity = new MovieUserEvaluation();
        entity.setMovieId(1);
        entity.setUserId(1);
        entity.setSatisfactionGrade(5);
        entity.setReview("Классный фильм");
        mueService.save(entity);
        return entity;
    }
}
