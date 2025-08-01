package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.entities.PropertyChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPropertyChangeLogRepository extends JpaRepository<PropertyChangeLog, Long> {
}