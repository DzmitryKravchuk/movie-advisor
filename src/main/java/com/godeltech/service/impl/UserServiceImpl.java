package com.godeltech.service.impl;

import com.godeltech.entity.User;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.exception.UpdateNotMatchIdException;
import com.godeltech.repository.UserRepository;
import com.godeltech.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public final class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(final User entity) {
        log.info("UserServiceImpl save {}", entity);
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        repository.save(entity);
    }

    @Override
    public User getById(final Integer id) {
        log.info("UserServiceImpl get by id: {}", id);
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(" Object with index " + id + " not found"));
    }

    @Override
    public List<User> getAll() {
        log.info("UserServiceImpl find ALL");
        return repository.findAll();
    }

    @Override
    public void delete(final Integer id) {
        log.info("UserServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void update(final User entity, final Integer id) {
        log.info("EmployeeServiceImpl update with id: {}", id);
        getById(id);
        if (!entity.getId().equals(id)) {
            throw new UpdateNotMatchIdException(" Object from request has index "
                    + entity.getId() + " and doesnt match index from url " + id);
        }
        save(entity);
    }

    @Override
    public void deleteAll() {
        log.info("deleteAll");
        repository.deleteAll();
    }
}