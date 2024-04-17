package com.example.project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Authentication {
	private final UserInformation user;
	private final String accessToken;
	private final String refreshToken;
	public final LocalDateTime expiredAt;

}
