package com.sleepwell.auth.utils;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private static final int EXPIRED_TIME = 3600 * 60 * 1000;
	public static final String USER_ID_CLAIM = "user_id";

	private final Key key;

	public JwtProvider() {
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}

	public String createToken(Long id) {
		Date now = new Date();
		Date expiredTime = new Date(now.getTime() + EXPIRED_TIME);

		return Jwts.builder()
			.claim(USER_ID_CLAIM, id)
			.setIssuedAt(now)
			.setExpiration(expiredTime)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}
}
