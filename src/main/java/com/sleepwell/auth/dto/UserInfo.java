package com.sleepwell.auth.dto;

import com.sleepwell.user.domain.SocialType;
import com.sleepwell.user.domain.User;

public record UserInfo(String socialId, String nickname, String profileImage, SocialType socialType) {
	public User toEntity() {
		return new User(socialId, socialType, nickname, profileImage);
	}
}
