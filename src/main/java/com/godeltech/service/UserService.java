package com.godeltech.service;

import com.godeltech.entity.User;

import java.util.List;

public interface UserService {
    void save(User entity);

    User getById(Integer id);

    List<User> getAll();

    void delete(Integer id);

    void update(User entity, Integer id);

    void deleteAll();
}
