package com.godeltech.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

//@Entity
//@EqualsAndHashCode(callSuper = true)
@Data
public class MovieUserEvaluation {//extends AbstractEntity{
 //   @ManyToOne(fetch = FetchType.LAZY, targetEntity = Movie.class)
    private Movie movie;

 //   @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    private User user;

    private int satisfactionGrade;

    private String review;
}
