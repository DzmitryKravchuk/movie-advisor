package com.godeltech.utils;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.dto.MovieEvaluationDTO;
import com.godeltech.entity.MovieUserEvaluation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieEvaluationDtoConverter {

    public static MovieEvaluationDTO convertToDTO(final MovieUserEvaluation mue, final String userName) {
        MovieEvaluationDTO dto = new MovieEvaluationDTO();
        dto.setReview(mue.getReview());
        dto.setReviewDate(convertToSimpleDateFormat(mue.getUpdated()));
        dto.setSatisfactionGrade(mue.getSatisfactionGrade());
        dto.setUserName(userName);

        return dto;
    }

    public static MovieUserEvaluation convertFromRequest(final EvaluationRequest evalRequest, final Integer userId) {
        MovieUserEvaluation mue = new MovieUserEvaluation();
        mue.setReview(evalRequest.getReview());
        mue.setSatisfactionGrade(evalRequest.getSatisfactionGrade());
        mue.setMovieId(evalRequest.getMovieId());
        mue.setUserId(userId);
        return mue;
    }

    private static String convertToSimpleDateFormat(final Date updated) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(updated);
    }
}
