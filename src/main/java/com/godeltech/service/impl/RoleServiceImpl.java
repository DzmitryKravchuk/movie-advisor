package com.godeltech.service.impl;

import com.godeltech.entity.Role;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.repository.RoleRepository;
import com.godeltech.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role getById(Integer id) {
        log.info("RoleServiceImpl get by id: {}", id);
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(" Object with index " + id + " not found"));
    }

    @Override
    public Role getByName(String name) {
        log.info("getByName: {}", name);
        return repository.findByRoleName(name).
                orElseThrow(() -> new ResourceNotFoundException(" Object with name " + name + " not found"));
    }

    @Override
    public void save(Role entity) {
        log.info("save {}", entity);
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public void deleteAll() {
        log.info("deleteAll");
        repository.deleteAll();
    }
}
