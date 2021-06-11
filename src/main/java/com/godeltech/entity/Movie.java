package com.godeltech.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

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
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class)
    @JsonManagedReference
    private Country country;
    @ManyToMany(mappedBy = "movies", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Genre> genres;
    @Transient
    private int avgSatisfactionGrade;
    @Transient
    Set<MovieUserEvaluation> movieEvaluations;
}
