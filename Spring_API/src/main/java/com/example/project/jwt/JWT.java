package com.example.project.jwt;

import java.time.LocalDateTime;
import java.util.List;

import com.example.project.dto.Authorized;

public interface JWT {
	String encode(int id, List<String> roles, LocalDateTime expiredAt, String secret);

	Authorized decode(String token, String secret);
}
