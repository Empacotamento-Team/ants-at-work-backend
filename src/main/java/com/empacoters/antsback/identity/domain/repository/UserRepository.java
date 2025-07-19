package com.empacoters.antsback.identity.domain.repository;

import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.shared.vo.Email;

public interface UserRepository {
    User findByEmail(Email email);
    User findById(Long id);
    boolean existsByEmail(Email email);
    boolean existsById(Long id);
    void deleteById(Long id);
    User save(User user);
}
