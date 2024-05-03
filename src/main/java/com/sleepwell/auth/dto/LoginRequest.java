package com.sleepwell.auth.dto;

import com.sleepwell.user.domain.SocialType;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
	@NotNull SocialType socialType,
	@NotNull String accessToken
) {
}
