package com.godeltech.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "mue")
@Data
public class MovieUserEvaluation {
    @Id
    private String id;
    private Integer movieId;
    private Integer userId;
    private int satisfactionGrade;
    private String review;
    private Date created;
    private Date updated;
}