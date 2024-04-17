package com.example.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationWithUsernameAndPassword {
	@NotBlank
	@Email
	private String username;
	@NotBlank
	private String password;
}
