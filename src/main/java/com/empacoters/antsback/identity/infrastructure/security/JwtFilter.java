package com.empacoters.antsback.identity.infrastructure.security;

import com.empacoters.antsback.identity.application.exception.ExpiredTokenException;
import com.empacoters.antsback.identity.application.exception.InvalidTokenException;
import com.empacoters.antsback.identity.application.exception.UserDoesNotExistException;
import com.empacoters.antsback.identity.application.usecases.AuthenticationUseCase;
import com.empacoters.antsback.shared.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final AuthenticationUseCase authenticationUseCase;

    public JwtFilter(AuthenticationUseCase authenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        var token = this.recoverToken(request);

        try {
            if (token != null) {
                var user = authenticationUseCase.authenticateToken(token);
                var userDetails = new UserDetailsImpl(user);
                if (user == null)
                    throw new UserDoesNotExistException("O usuário com este token não existe.");

                var authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            var expired = e instanceof ExpiredTokenException;

            var apiError = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.name(),
                expired ? "TOKEN_EXPIRADO" : "TOKEN_INVALIDO",
                e.getMessage()
            );

            String json = new ObjectMapper().writeValueAsString(apiError);
            response.getWriter().write(json);
        } catch (UserDoesNotExistException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");

            var apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                "USUARIO_NAO_EXISTE",
                e.getMessage()
            );

            String json = new ObjectMapper().writeValueAsString(apiError);
            response.getWriter().write(json);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer"))
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
