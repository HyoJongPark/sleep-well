package com.sleepwell.user.domain;

import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.common.error.exception.ErrorCode;

public enum Role {
	NORMAL, ADMIN;

	public static Role from(String role) {
		try {
			return valueOf(role);
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new BadRequestException(ErrorCode.INVALID_ROLE_NAME);
		}
	}

	public boolean verifyAccessPermissions(Role role) {
		if (this == NORMAL) {
			return true;
		}
		return this == role;
	}
}
