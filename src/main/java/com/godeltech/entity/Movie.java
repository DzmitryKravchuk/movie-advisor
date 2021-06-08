package com.godeltech.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Movie extends AbstractEntity {
    @Column
    private String title;
    @Column
    private int releaseYear;
    @Column
    private String director;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JsonManagedReference
    private Country country;
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movieId"), inverseJoinColumns = @JoinColumn(name = "genreId"))
    private List <Genre> genreList;

    private int avgSatisfactionGrade;

   // @OneToMany(mappedBy = "movieUserEvaluation", fetch = FetchType.LAZY)
//    private List<MovieUserEvaluation> evaluationList;
}
