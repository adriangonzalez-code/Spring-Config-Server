package com.driagon.services.configserver.configs;

import com.driagon.services.configserver.filters.JwtAuthenticationFilter;
import com.driagon.services.configserver.filters.ScopeKeyAuthFilter;
import com.driagon.services.configserver.providers.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ScopeKeyAuthFilter scopeKeyAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationProvider authenticationProvider) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(scopeKeyAuthFilter, JwtAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (autenticación)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Rutas POST - acceso solo para ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/scopes/*/access-key").hasRole("ADMIN")

                        // Rutas GET - acceso para ADMIN y USER
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/scopes/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/properties/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/ui/**").hasAnyRole("ADMIN", "USER")

                        // Ruta POST - acceso para SCOPE_KEY_AUTH
                        .requestMatchers(HttpMethod.POST,"/api/driver/properties").hasRole("SCOPE_KEY_AUTH")

                        // Rutas POST, PUT, DELETE - solo ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/scopes/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/scopes/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/properties/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/properties/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/properties/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/ui/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/ui/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/ui/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/driver/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/driver/**").hasRole("ADMIN")

                        .anyRequest().denyAll()
                ).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustomAuthenticationProvider authenticationProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:61483/", "http://localhost:59574/"));
        configuration.setAllowedMethods(List.of(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).toArray(String[]::new)));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}