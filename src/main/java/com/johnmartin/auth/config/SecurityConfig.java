package com.johnmartin.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.security.JwtUtil;
import com.johnmartin.auth.security.custom.CustomAuthEntryPoint;
import com.johnmartin.auth.security.filter.CorrelationIdFilter;
import com.johnmartin.auth.security.filter.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final CustomAuthEntryPoint authEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    public SecurityConfig(CustomAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   CorrelationIdFilter correlationIdFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(correlationIdFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
            .authorizeHttpRequests(authorize -> authorize.requestMatchers(ApiConstants.Path.API_AUTH + "/**",
                                                                          ApiConstants.Path.ACTUATOR + ApiConstants.Path.HEALTH,
                                                                          ApiConstants.Path.ACTUATOR + ApiConstants.Path.HEALTH + "/**")
                                                         .permitAll()
                                                         .requestMatchers(ApiConstants.Path.ACTUATOR + "/**")
                                                         .denyAll()
                                                         .anyRequest()
                                                         .authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
