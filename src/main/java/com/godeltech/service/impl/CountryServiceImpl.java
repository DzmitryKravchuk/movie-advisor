package com.godeltech.service.impl;

import com.godeltech.entity.Country;
import com.godeltech.exception.ServiceEntityNotFoundException;
import com.godeltech.exception.ServiceUpdateNotMatchIdException;
import com.godeltech.repository.CountryRepository;
import com.godeltech.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;

    @Override
    public void save(Country entity) {
        log.info("CountryServiceImpl save {}", entity);
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public Country getById(Integer id) {
        log.info("CountryServiceImpl get by id: {}", id);
        return repository.findById(id).
                orElseThrow(() -> new ServiceEntityNotFoundException(" Object with index " + id + " not found"));
    }

    @Override
    public List<Country> getAll() {
        log.info("CountryServiceImpl find ALL");
        return repository.findAll();
    }

    @Override
    public void delete(Integer id) {
        log.info("CountryServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void update(Country entity, Integer id) {
        log.info("CountryServiceImpl update with id: {}", id);
        getById(id);
        if (!entity.getId().equals(id)){
            throw new ServiceUpdateNotMatchIdException(" Object from request has index "+ entity.getId()+" and doesnt match index from url "+ id);
        }
        save(entity);
    }
}