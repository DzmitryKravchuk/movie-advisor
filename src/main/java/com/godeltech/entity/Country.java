package com.godeltech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table
public class Country extends AbstractEntity{
    @Column
    private String countryName;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Movie> movies= new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Country country = (Country) o;
        return countryName.equals(country.countryName) && Objects.equals(movies, country.movies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), countryName);
    }
}
