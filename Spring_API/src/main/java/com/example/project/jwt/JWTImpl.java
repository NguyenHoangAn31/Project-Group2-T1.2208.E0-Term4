package com.example.project.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.extern.slf4j.Slf4j;
import com.example.project.dto.Authorized;

@Slf4j
public class JWTImpl implements JWT {

	private SecretKey secretToKey(String secret) {
		var bytes = secret.getBytes(Strings.UTF_8);
		try {
			log.info("Creating jwt key");
			return Keys.hmacShaKeyFor(bytes);
		} catch (WeakKeyException e) {
			log.info("Creating jwt key with weakkey");
			return Keys.hmacShaKeyFor(Arrays.copyOf(bytes, 64));
		}
	}

	@Override
	public String encode(int id, List<String> roles, LocalDateTime expiredAt, String secret) {
		log.info("Creating new jwt, id: [{}], roles: [{}]", id, roles.toString());
		var accessToken = Jwts.builder()
				.setSubject(String.valueOf(id))
				.claim("roles", String.join(",", roles))
				.setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(secretToKey(secret))
				.compact();
		log.info("Successful created new jwt: [{}]", accessToken);
		return accessToken;
	}

	@Override
	public Authorized decode(String token, String secret) {
		log.info("Decode jwt");
		var decoded = Jwts.parser()
				.setSigningKey(secretToKey(secret))
				.build()
				.parseClaimsJws(token);
		
		var id = decoded
				.getBody()
				.getSubject();
		
		var rolesString = decoded
				.getBody()
				.get("roles")
				.toString();
		
		var roles = rolesString.split(",");
		var authorities = Arrays.stream(roles)
				.map(r->new SimpleGrantedAuthority(r))
				.toList();
		
		return new Authorized(Integer.parseInt(id), authorities);
	}



}
