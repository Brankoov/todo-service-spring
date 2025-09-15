package se.brankoov.todoservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {

    private static final long LIMIT_WINDOW_MS = 60_000; // 1 minute
    private static final int MAX_REQUESTS = 50; // max requests per window

    private final Map<String, UserRequests> requestCounts = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        UserRequests userRequests = requestCounts.computeIfAbsent(ip, k -> new UserRequests());

        synchronized (userRequests) {
            long now = Instant.now().toEpochMilli();
            if (now - userRequests.windowStart > LIMIT_WINDOW_MS) {
                userRequests.windowStart = now;
                userRequests.count = 0;
            }

            if (userRequests.count >= MAX_REQUESTS) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too many requests");
                return;
            }
            userRequests.count++;
        }

        filterChain.doFilter(request, response);
    }

    private static class UserRequests {
        long windowStart = Instant.now().toEpochMilli();
        int count = 0;
    }
}
