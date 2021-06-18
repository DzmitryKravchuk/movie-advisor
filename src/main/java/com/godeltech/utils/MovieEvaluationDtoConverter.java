package com.godeltech.utils;

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

    private static String convertToSimpleDateFormat(final Date updated) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(updated);
    }
}
