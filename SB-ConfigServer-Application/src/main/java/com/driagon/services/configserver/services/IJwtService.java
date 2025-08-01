package com.driagon.services.configserver.services;

import com.driagon.services.configserver.dto.responses.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    AuthResponse getAuthResponse(UserDetails userDetails);
}