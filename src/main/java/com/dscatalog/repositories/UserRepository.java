package com.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dscatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {}
