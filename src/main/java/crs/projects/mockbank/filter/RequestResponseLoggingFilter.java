package crs.projects.mockbank.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(RequestResponseLoggingFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info(String.format("Starting request %s %s", request.getMethod(), request.getRequestURI()));

        long startTime = System.currentTimeMillis();

        filterChain.doFilter(request, response);

        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;

        logger.info(String.format("Finished request %s %s with status %s, elapsed time: %s", request.getMethod(), request.getRequestURI(), response.getStatus(), elapsedTime));
    }
}
