package com.example.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


import com.example.project.middleware.AuthenticationMiddleware;

import com.example.project.util.PublicRoutes;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


	@Autowired
	private AuthenticationMiddleware authenticationMiddleware;



	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	@Order(1)
	SecurityFilterChain api(HttpSecurity http) throws Exception {
		PublicRoutes.PublicRoutesManager.publicRoutes().add(HttpMethod.POST, "/api/login").injectOn(http);

		http.csrf(AbstractHttpConfigurer::disable).securityMatcher("/api/**")
				.authorizeHttpRequests(request -> request.anyRequest().authenticated())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authenticationMiddleware, UsernamePasswordAuthenticationFilter.class)
				.cors(configurer -> new CorsConfiguration().applyPermitDefaultValues());
		return http.build();
	}

}
