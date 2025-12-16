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
import com.johnmartin.auth.security.JwtAuthenticationFilter;
import com.johnmartin.auth.security.custom.CustomAccessDeniedHandler;
import com.johnmartin.auth.security.custom.CustomAuthEntityPoint;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAuthEntityPoint customAuthEntityPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF with lambda
            .csrf(AbstractHttpConfigurer::disable)

            // This allows Public APIs (login/register), Internal (service-to-service) APIs and Health for status update
            .authorizeHttpRequests(authorize -> authorize.requestMatchers(ApiConstants.Path.API_AUTH + "/**",
                                                                          ApiConstants.Path.API_BASE_V1_INTERNAL
                                                                                                              + "/**",
                                                                          ApiConstants.Path.HEALTH)
                                                         .permitAll()
                                                         .anyRequest()
                                                         .authenticated())

            .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthEntityPoint))
            .exceptionHandling(ex -> ex.accessDeniedHandler(customAccessDeniedHandler))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(basic -> basic.realmName(ApiConstants.APP_NAME));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
