package com.johnmartin.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.johnmartin.auth.entities.RoleEntity;
import com.johnmartin.auth.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<RoleEntity> getRole(int role) {
        return roleRepository.findById(role);
    }
}
