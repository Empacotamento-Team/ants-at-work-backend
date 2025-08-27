package com.empacoters.antsback.identity.interfaces.dto;

import com.empacoters.antsback.identity.domain.model.UserRole;

public record UserDTO(
   Long id,
   String name,
   String email,
   UserRole[] roles
) {}
