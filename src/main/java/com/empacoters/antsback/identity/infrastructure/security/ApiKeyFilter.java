package com.empacoters.antsback.identity.infrastructure.security;

import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.model.UserRole;
import com.empacoters.antsback.shared.vo.Email;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    @Value("${security.api-key}")
    private String validApiKey;

    @Value("${security.api-key.enabled}")
    private boolean apiKeyEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        var apiKey = recoverApiKey(request);
        if (!apiKeyEnabled || apiKey == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!validApiKey.equals(apiKey)) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "API key inválida");
            return;
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(UserRole.ADMIN.toAuthority()));
        var set = new HashSet<UserRole>();
        set.add(UserRole.ADMIN);
        set.add(UserRole.MANAGER);

        var user = new User(0L, "AntsAtWorkSystem", new Email("antsatwork@work.com"), "osdkfjds",
                set);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private String recoverApiKey(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("ApiKey "))
            return null;
        return authHeader.replace("ApiKey ", "");
    }
}

