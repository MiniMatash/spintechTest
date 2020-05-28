package com.minimatash.spintech.repository;

import com.minimatash.spintech.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}