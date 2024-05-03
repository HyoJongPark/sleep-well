package com.sleepwell.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sleepwell.user.domain.SocialType;

public record GoogleUserInfo(
	@JsonProperty("id") String socialId,
	@JsonProperty("name") String nickname,
	@JsonProperty("picture") String profileImage
) {

	public UserInfo toUserInfo() {
		return new UserInfo(socialId, nickname, profileImage, SocialType.GOOGLE);
	}
}
