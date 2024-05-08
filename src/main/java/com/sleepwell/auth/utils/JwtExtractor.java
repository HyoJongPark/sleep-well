package com.sleepwell.auth.utils;

import static com.sleepwell.auth.utils.JwtProvider.*;

import java.security.Key;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sleepwell.auth.dto.Principle;
import com.sleepwell.user.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;

@Component
public class JwtExtractor {

	private static final String JWT_TOKEN_NAME = "utk";
	private static final String ERROR_MESSAGE_TOKEN_NOT_EXIST = "인증 토큰이 존재하지 않습니다.";
	private static final String ERROR_MESSAGE_INVALID_JWT_SIGNATURE = "잘못된 JWT 서명입니다.";
	private static final String ERROR_MESSAGE_EXPIRED_JWT = "만료된 JWT 토큰입니다.";
	private static final String ERROR_MESSAGE_NOT_SUPPORT_JWT = "지원되지 않는 JWT 토큰입니다.";
	private static final String ERROR_MESSAGE_INVALID_JWT_FORMAT = "잘못된 JWT 토큰 형식입니다.";

	private final JwtParser jwtParser;

	public JwtExtractor() {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		this.jwtParser = Jwts.parserBuilder()
			.setSigningKey(key)
			.build();
	}

	public Principle extract(Cookie[] cookies) {
		Cookie token = parseCookie(cookies)
			.orElseThrow(() -> new RuntimeException(ERROR_MESSAGE_TOKEN_NOT_EXIST));
		Claims claims = parseClaims(token.getValue());

		Long id = claims.get(USER_ID_CLAIM, Long.class);
		Role role = claims.get(ROLE_CLAIM, Role.class);
		return new Principle(id, role);
	}

	private Optional<Cookie> parseCookie(Cookie[] cookies) {
		if (cookies == null) {
			return Optional.empty();
		}

		return Arrays.stream(cookies)
			.filter(cookie -> cookie.getName().equals(JWT_TOKEN_NAME))
			.findFirst();
	}

	private Claims parseClaims(String token) {
		try {
			return jwtParser.parseClaimsJws(token)
				.getBody();
		} catch (SecurityException | MalformedJwtException exception) {
			throw new RuntimeException(ERROR_MESSAGE_INVALID_JWT_SIGNATURE);
		} catch (ExpiredJwtException e) {
			throw new RuntimeException(ERROR_MESSAGE_EXPIRED_JWT);
		} catch (UnsupportedJwtException e) {
			throw new RuntimeException(ERROR_MESSAGE_NOT_SUPPORT_JWT);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(ERROR_MESSAGE_INVALID_JWT_FORMAT);
		}
	}
}
