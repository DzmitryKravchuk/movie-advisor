package com.godeltech.service;

import com.godeltech.entity.Role;

public interface RoleService {

    Role getById(Integer id);

    Role getByName(String name);

    void save(Role entity);

    void deleteAll();
}
