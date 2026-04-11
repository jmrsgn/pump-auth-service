package com.johnmartin.auth.service;

import org.springframework.stereotype.Service;

import com.johnmartin.auth.constants.api.ApiErrorConstants;
import com.johnmartin.auth.entity.RoleEntity;
import com.johnmartin.auth.exception.NotFoundException;
import com.johnmartin.auth.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity getRole(int role) {
        return roleRepository.findById(role).orElseThrow(() -> new NotFoundException(ApiErrorConstants.ROLE_NOT_FOUND));
    }
}
