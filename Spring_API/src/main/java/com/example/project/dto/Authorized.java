package com.example.project.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class Authorized extends User {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;

	public Authorized(com.example.project.entity.User user) {
		super(user.getEmail(), user.getPassword(), true, true, true, true, user.getGrantedAuthorities());

	}

	public Authorized(int id, List<SimpleGrantedAuthority> authorities) {
		super("USERNAME", "PASSWORD", authorities);
		this.id = id;
		this.name = "";
	}

	public Boolean isAdmin() {
		return getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"));
	}

	public Boolean meOrSessionIsAdmin(int id) {
		Boolean admin = isAdmin();
		Boolean itsMe = getId() == id;
		if (admin) {
			return true;
		}
		return itsMe;
	}

	public static Optional<Authorized> current() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.map(org.springframework.security.core.Authentication::getPrincipal).map(p -> {
					if (p instanceof Authorized au) {
						return au;
					}
					return null;
				});
	}

	public UsernamePasswordAuthenticationToken getAuthentication() {
		return new UsernamePasswordAuthenticationToken(this, null, getAuthorities());
	}

}
