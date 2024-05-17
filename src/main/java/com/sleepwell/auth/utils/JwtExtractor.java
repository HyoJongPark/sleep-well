package com.sleepwell.auth.utils;

import static com.sleepwell.auth.utils.JwtProvider.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sleepwell.auth.dto.Principle;
import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.common.error.exception.UnAuthorizedException;
import com.sleepwell.user.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;

@Component
public class JwtExtractor {

	private static final String JWT_TOKEN_NAME = "utk";

	private final JwtParser jwtParser;

	public JwtExtractor(@Value("${sleep-well.secret-key}") String secretKey) {
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.jwtParser = Jwts.parserBuilder()
			.setSigningKey(key)
			.build();
	}

	public Principle extract(Cookie[] cookies) {
		Cookie token = parseCookie(cookies)
			.orElseThrow(() -> new UnAuthorizedException(ErrorCode.TOKEN_NOT_EXIST));
		Claims claims = parseClaims(token.getValue());

		Long id = claims.get(USER_ID_CLAIM, Long.class);
		String role = claims.get(ROLE_CLAIM, String.class);
		return new Principle(id, Role.from(role));
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
			throw new UnAuthorizedException(ErrorCode.INVALID_JWT_SIGNATURE);
		} catch (ExpiredJwtException e) {
			throw new UnAuthorizedException(ErrorCode.EXPIRED_JWT);
		} catch (UnsupportedJwtException e) {
			throw new UnAuthorizedException(ErrorCode.NOT_SUPPORT_JWT);
		} catch (IllegalArgumentException e) {
			throw new UnAuthorizedException(ErrorCode.INVALID_JWT_FORMAT);
		}
	}
}
