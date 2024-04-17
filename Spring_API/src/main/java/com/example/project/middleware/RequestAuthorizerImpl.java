package com.example.project.middleware;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.project.dto.Authorized;
import com.example.project.jwt.JWT;
import com.example.project.util.PublicRoutes;

@Component
public class RequestAuthorizerImpl implements RequestAuthorizer {

	@Value("jwt.secret")
	private String JWT_SECRET;

	@Autowired
	private JWT jwt;

	@Override
	public void tryAuthorizer(HttpServletRequest request, HttpServletResponse response) {
		if (PublicRoutes.PublicRoutesManager.publicRoutes().anyMatch(request)) {
			return;
		}

		var token = Authorization.extract(request);
		if (Objects.isNull(token)) {
			return;
		}

		try {
			Authorized authorized = jwt.decode(token, JWT_SECRET);

			SecurityContextHolder.getContext().setAuthentication(authorized.getAuthentication());

		} catch (Exception e) {
			ResponseEntity.status(403).build();
		}
	}

}
