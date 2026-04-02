package com.johnmartin.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnmartin.auth.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
