package com.godeltech.repository;

import com.godeltech.entity.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @EntityGraph(attributePaths = {"movies"})
    Genre findOneById(Integer genreId);
}
