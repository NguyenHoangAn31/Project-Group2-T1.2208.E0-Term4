package com.example.project.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestAuthorizer {
	void tryAuthorizer(HttpServletRequest request, HttpServletResponse response);
}
