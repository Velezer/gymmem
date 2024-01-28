package ariefsyaifu.gymmem.security;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import ariefsyaifu.gymmem.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = Optional.ofNullable(request.getHeader("Authorization")).orElse("");

        String uri = request.getRequestURI();
        if (uri.startsWith("/api/v1/auth")) {
            chain.doFilter(request, response);
            return;
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            if (uri.startsWith("/api/v1/subscription") ||
                    uri.startsWith("/api/v1/payment/transaction/history")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            chain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(7);

        Claims claims = null;
        try {
            claims = jwtUtil.extractAllClaims(token);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        request.setAttribute("claims", claims);
        chain.doFilter(request, response);
    }
}