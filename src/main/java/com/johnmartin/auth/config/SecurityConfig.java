package com.johnmartin.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)

            // This allows Public APIs (login/register), Internal (service-to-service) APIs and Health for status update
            .authorizeHttpRequests(authorize -> authorize.requestMatchers(ApiConstants.Path.API_AUTH + "/**",
                                                                          ApiConstants.Path.HEALTH)
                                                         .permitAll()
                                                         .anyRequest()
                                                         .authenticated())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(correlationIdFilter(), UsernamePasswordAuthenticationFilter.class)
            .httpBasic(basic -> basic.realmName(ApiConstants.APP_NAME));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
