package com.sleepwell.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sleepwell.auth.dto.LoginRequest;
import com.sleepwell.auth.service.AuthService;
import com.sleepwell.auth.utils.JwtProvider;
import com.sleepwell.user.domain.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private static final String ACCESS_TOKEN_NAME = "utk";

	private final AuthService authService;
	private final JwtProvider jwtProvider;

	@PostMapping("/google")
	public ResponseEntity<Void> googleAuthLogin(@RequestBody @Valid LoginRequest loginRequest,
		HttpServletResponse response) {
		User user = authService.login(loginRequest.socialType(), loginRequest.accessToken());
		String token = jwtProvider.createToken(user.getId(), user.getRole());

		Cookie cookie = new Cookie(ACCESS_TOKEN_NAME, token);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

		return ResponseEntity.ok().build();
	}
}
