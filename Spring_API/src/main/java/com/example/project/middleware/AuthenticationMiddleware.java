package com.example.project.middleware;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@Order(1)
@AllArgsConstructor
public class AuthenticationMiddleware extends OncePerRequestFilter {

	private final RequestAuthorizer requestAuthorizer;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		requestAuthorizer.tryAuthorizer(request, response);
		filterChain.doFilter(request, response);
	}

}
