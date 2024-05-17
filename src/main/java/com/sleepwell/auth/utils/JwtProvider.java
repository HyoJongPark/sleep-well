package com.sleepwell.auth.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sleepwell.user.domain.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private static final int EXPIRED_TIME = 3600 * 60 * 1000;
	public static final String USER_ID_CLAIM = "user_id";
	public static final String ROLE_CLAIM = "role";

	private final Key key;

	public JwtProvider(@Value("${sleep-well.secret-key}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String createToken(Long id, Role role) {
		Date now = new Date();
		Date expiredTime = new Date(now.getTime() + EXPIRED_TIME);

		return Jwts.builder()
			.claim(USER_ID_CLAIM, id)
			.claim(ROLE_CLAIM, role)
			.setIssuedAt(now)
			.setExpiration(expiredTime)
			.signWith(key)
			.compact();
	}
}
