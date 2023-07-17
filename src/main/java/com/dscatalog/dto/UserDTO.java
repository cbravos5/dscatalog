package com.dscatalog.dto;

import java.util.HashSet;
import java.util.Set;

import com.dscatalog.entities.User;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        Set<RoleDTO> roles
        ) {

    public UserDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), new HashSet<>());
    }
}
