package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Set<User> findByEmailInIgnoreCase(Set<String> emails);
}