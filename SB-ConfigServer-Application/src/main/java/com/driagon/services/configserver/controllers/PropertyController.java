package com.driagon.services.configserver.controllers;

import com.driagon.services.configserver.dto.requests.SetPropertyRequest;
import com.driagon.services.configserver.dto.responses.SetPropertyResponse;
import com.driagon.services.configserver.services.IPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
@Validated
public class PropertyController {

    private final IPropertyService propertyService;

    @GetMapping("/{scopeId}/scope")
    public ResponseEntity<Set<SetPropertyResponse>> getAllPropertiesByScope(@PathVariable String scopeId) {
        Set<SetPropertyResponse> properties = propertyService.getAllPropertiesByScope(Long.valueOf(scopeId));
        if (properties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(properties);
    }

    @PutMapping("/{scopeId}/scope")
    public ResponseEntity<Set<SetPropertyResponse>> setProperties(@PathVariable String scopeId, @RequestBody Set<SetPropertyRequest> request) {
        Set<SetPropertyResponse> responses = propertyService.setProperties(Long.valueOf(scopeId), request);
        return ResponseEntity.ok(responses);
    }
}