package com.godeltech.dto;

import lombok.Data;

@Data
public class MovieEvaluationDTO {
    private String userName;
    private int satisfactionGrade;
    private String review;
    private String reviewDate;
}
