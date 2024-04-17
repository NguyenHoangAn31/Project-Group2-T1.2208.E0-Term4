package com.example.project.middleware;

import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;

public class Authorization {
	private Authorization() {
	}

	public static String extract(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (tokenIsNull(token)) {
			return null;
		}

		String sBearer = "Bearer";

		if (token.substring(sBearer.length()).equals(sBearer)) {
			return null;
		}

		return token.substring(sBearer.length() + 1);
	}

	public static boolean tokenIsNull(String token) {
		return Objects.isNull(token) || !token.startsWith("Bearer");
	}
}
