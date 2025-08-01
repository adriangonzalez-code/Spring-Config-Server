package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
}