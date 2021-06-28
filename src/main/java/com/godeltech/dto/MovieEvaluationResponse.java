package com.godeltech.dto;

import lombok.Data;

@Data
public class MovieEvaluationResponse {
    private String userName;
    private int satisfactionGrade;
    private String review;
    private String reviewDate;
}
