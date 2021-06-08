package com.godeltech.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Country extends AbstractEntity{
    @Column
    private String countryName;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Movie> movies;
}
