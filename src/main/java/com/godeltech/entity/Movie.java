package com.godeltech.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "movie_genre", joinColumns = {@JoinColumn(name = "movieId")}, inverseJoinColumns = {@JoinColumn(name = "genreId")})
    private Set<Genre> genres = new HashSet<>();
    @Transient
    private int avgSatisfactionGrade;
    @Transient
    private List<MovieUserEvaluation> movieEvaluations;

    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Movie{"
                + "title='" + title + '\''
                + ", releaseYear=" + releaseYear
                + ", director='" + director + '\''
                + ", description='" + description + '\''
                + ", country=" + country.getCountryName()
                + ", genres=" + genres.stream().map(Genre::getGenreName).collect(Collectors.toList())
                + ", avgSatisfactionGrade=" + avgSatisfactionGrade
                + ", movieEvaluations=" + movieEvaluations
                + '}';
    }
}
