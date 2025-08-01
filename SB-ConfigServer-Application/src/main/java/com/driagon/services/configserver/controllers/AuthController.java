package com.driagon.services.configserver.controllers;

import com.driagon.services.configserver.dto.requests.AuthRequest;
import com.driagon.services.configserver.dto.responses.AuthResponse;
import com.driagon.services.configserver.services.IJwtService;
import com.driagon.services.error.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException("Invalid credentials");
        }
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        AuthResponse authResponse = jwtService.getAuthResponse(user);

        return ResponseEntity.ok(authResponse);
    }
}