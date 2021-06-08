package com.godeltech.service.impl;

import com.godeltech.entity.Role;
import com.godeltech.exception.EntityNotFoundException;
import com.godeltech.repository.RoleRepository;
import com.godeltech.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role getById(Integer id) {
        log.info("RoleServiceImpl get by id: {}", id);
        return repository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(" Object with index " + id + " not found"));
    }
}
