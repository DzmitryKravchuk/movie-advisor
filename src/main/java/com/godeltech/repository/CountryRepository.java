package com.godeltech.repository;

import com.godeltech.entity.Country;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    @EntityGraph(attributePaths = {"movies"})
    Country findOneById(Integer id);
}
