package com.johnmartin.auth.security;

import java.io.IOException;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (StringUtils.isBlank(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header and retrieve the email from it
        String token = StringUtils.substringAfter(header, "Bearer ");
        String email = jwtUtil.extractEmail(token);

        // Check that the username exists, and no authentication is currently set in the security context,
        // and the provided JWT token is valid
        if (StringUtils.isNotBlank(email) && SecurityContextHolder.getContext().getAuthentication() == null
            && jwtUtil.validateToken(token)) {

            // Create an authentication token using the extracted username and no credentials or authorities
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,
                                                                                                    null, // Already
                                                                                                    // provided
                                                                                                    // JWT
                                                                                                    Collections.emptyList());

            // Attach request details (like remote address and session ID) to the authentication token
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Store the authentication token in the SecurityContext, marking the user as authenticated
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Continue with the remaining filters in the chain
        filterChain.doFilter(request, response);
    }
}
