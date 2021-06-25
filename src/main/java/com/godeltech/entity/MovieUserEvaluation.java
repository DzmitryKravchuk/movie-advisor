package com.godeltech.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "#{@environment.getProperty('spring.data.mongodb.collection-name')}")
@Data
public class MovieUserEvaluation {

    @Id
    private String id;
    private Integer movieId;
    private Integer userId;
    private int satisfactionGrade;
    private String review;
    private LocalDate created;
    private LocalDate updated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieUserEvaluation that = (MovieUserEvaluation) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}