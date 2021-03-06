package com.godeltech.service;

import com.godeltech.entity.Country;

import java.util.List;

public interface CountryService {
    void save(Country entity);

    Country getById(Integer id);

    Country getCountryWithMoviesByCountryId(Integer id);

    List<Country> getAll();

    void delete(Integer id);

    void update(Country entity, Integer id);

    void deleteAll();

    Country getByCountryName(String country);
}
