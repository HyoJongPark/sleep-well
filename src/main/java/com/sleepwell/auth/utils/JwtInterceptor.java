package com.sleepwell.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sleepwell.auth.dto.Principle;
import com.sleepwell.user.domain.Role;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

	private final Role role;

	@Autowired
	private final JwtExtractor jwtExtractor;
	@Autowired
	private final AuthenticationStorage authenticationStorage;

	public JwtInterceptor(Role role, JwtExtractor jwtExtractor, AuthenticationStorage authenticationStorage) {
		this.role = role;
		this.jwtExtractor = jwtExtractor;
		this.authenticationStorage = authenticationStorage;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Principle principle = jwtExtractor.extract(request.getCookies());

		if (principle.role() != this.role) {
			throw new RuntimeException("접근 권한이 올바르지 않습니다.");
		}

		authenticationStorage.set(principle);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		@Nullable Exception ex) {
		authenticationStorage.remove();
	}
}
