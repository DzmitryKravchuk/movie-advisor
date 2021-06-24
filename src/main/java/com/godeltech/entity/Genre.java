package com.godeltech.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table
public class Genre extends AbstractEntity {
    @Column
    private String genreName;

    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "genres", fetch = FetchType.LAZY)
       private Set<Movie> movies = new HashSet<>();

    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
