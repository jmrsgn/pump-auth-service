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
import com.johnmartin.auth.security.custom.AuthEntryPoint;
import com.johnmartin.auth.security.filter.CorrelationIdFilter;
import com.johnmartin.auth.security.filter.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AuthEntryPoint authEntryPoint;

    public SecurityConfig(AuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   CorrelationIdFilter correlationIdFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)

            // This allows Public APIs (login/register), Internal (service-to-service) APIs and Health for status update
            .authorizeHttpRequests(authorize -> authorize.requestMatchers(ApiConstants.Path.API_AUTH + "/**",
                                                                          ApiConstants.Path.HEALTH,
                                                                          ApiConstants.Path.HEALTH + "/**")
                                                         .permitAll()
                                                         .anyRequest()
                                                         .authenticated())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
            .addFilterBefore(correlationIdFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
