package com.sleepwell.auth.dto;

import com.sleepwell.user.domain.Role;

public record Principle(
	Long id,
	Role role
) {
}
