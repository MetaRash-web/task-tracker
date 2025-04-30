package com.metarash.backend.security.jwt;

import com.metarash.backend.security.CustomUserDetails;
import com.metarash.backend.security.CustomUserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserServiceImpl customUserService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/auth/refresh")) {
            log.info("Refresh token request, bypassing JWT validation");
            filterChain.doFilter(request, response);
            return;
        }

        if (token != null) {
            log.info("Processing request with token: {}", token);
            if (jwtService.validateJwtToken(token)) {
                log.info("Valid JWT token found, setting user details in SecurityContextHolder");
                setCustomUserDetailsToSecurityContextHolder(token);
            } else {
                log.warn("Invalid JWT token: {}", token);
            }
        } else {
            log.warn("No JWT token found in request");
        }

        filterChain.doFilter(request, response);
    }

    private void setCustomUserDetailsToSecurityContextHolder(String token) {

        String email = jwtService.getEmailFromToken(token);
        try {
            log.info("Loading user details for email: {}", email);
            CustomUserDetails customUserDetails = customUserService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            log.error("Error loading user details for token: {}", token, e);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
