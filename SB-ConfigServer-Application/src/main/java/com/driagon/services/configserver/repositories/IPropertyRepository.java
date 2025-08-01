package com.driagon.services.configserver.repositories;

import com.driagon.services.configserver.constants.QueriesConstants;
import com.driagon.services.configserver.entities.Property;
import com.driagon.services.configserver.entities.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface IPropertyRepository extends JpaRepository<Property, Long> {

    Set<Property> findByScope_Id(Long scopeId);

    @Query(QueriesConstants.PropertyQueries.FIND_BY_SCOPE_ID)
    List<Property> findByScopeIdAndKeyIn(@Param("scopeId") Long scopeId, @Param("keys") Set<String> keys);

    boolean existsByIdAndScope_Id(Long id, Long scopeId);
}