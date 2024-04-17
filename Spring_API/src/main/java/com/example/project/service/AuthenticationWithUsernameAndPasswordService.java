package com.example.project.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.dto.Authentication;
import com.example.project.dto.AuthenticationWithUsernameAndPassword;
import com.example.project.dto.UserInformation;
import com.example.project.entity.RefreshToken;
import com.example.project.entity.User;
import com.example.project.jwt.JWT;
import com.example.project.repository.RefreshTokenRepository;
import com.example.project.repository.UserRepository;

@Service
public class AuthenticationWithUsernameAndPasswordService {

	@Value("jwt.secret")
	public String TOKEN_SERCRET;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWT jwt;

	@Autowired
	private PasswordEncoder encoder;

	public Authentication processLogin(AuthenticationWithUsernameAndPassword body) {
		String email = body.getUsername();
		String password = body.getPassword();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid Username or Password"));

		// so sánh password
		if (!encoder.matches(password, user.getPassword())) {
			throw new UsernameNotFoundException("Invalid Username or Password");
		}

		var expiredAt = LocalDateTime.now().plusDays(1);
		var accessToken = jwt.encode(user.getId(), user.getAuthorities(), expiredAt, TOKEN_SERCRET);

		// tạo refresh token
		refreshTokenRepository.disableOldRefreshTokenFromUser(user.getId());
		RefreshToken refresh = new RefreshToken(user, 7);
		refreshTokenRepository.save(refresh);

		Authentication auth = new Authentication(new UserInformation(user), accessToken, refresh.getCode(), expiredAt);
		return auth;
	}
}
