package br.uniesp.si.techback.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RequestLoggingFilter
        extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long inicio =
                System.currentTimeMillis();

        log.info(
                "INICIO {} {} correlation-id={}",
                request.getMethod(),
                request.getRequestURI(),
                MDC.get("correlation-id")
        );

        filterChain.doFilter(
                request,
                response
        );

        long fim =
                System.currentTimeMillis();

        log.info(
                "FIM {} {} status={} tempo={}ms correlation-id={}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                (fim - inicio),
                MDC.get("correlation-id")
        );
    }
}
