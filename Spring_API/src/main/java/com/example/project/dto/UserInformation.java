package com.example.project.dto;

import java.util.List;

import lombok.Getter;
import com.example.project.entity.User;

@Getter
public class UserInformation {
	private final int id;
	private final String email;
	private final List<String> roles;

	public UserInformation(User user) {
		id = user.getId();
		email = user.getEmail();
		roles = user.getAuthorities();
	}
}
