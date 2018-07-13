package com.example.demo.model;

import com.example.demo.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    boolean existsByUsername(String username);
}
