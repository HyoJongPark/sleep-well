package com.sleepwell.auth.utils.oauth;

import com.sleepwell.auth.dto.UserInfo;
import com.sleepwell.user.domain.SocialType;

public interface OAuth2Client {

	SocialType getSocialType();

	UserInfo getUserInfo(String accessToken);
}
