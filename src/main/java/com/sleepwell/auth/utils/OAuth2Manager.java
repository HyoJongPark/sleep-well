package com.sleepwell.auth.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sleepwell.user.domain.SocialType;

@Component
public class OAuth2Manager {

	private static final String ERROR_MESSAGE_ALREADY_EXIST_SOCIAL_TYPE = "이미 존재하는 SocialType 입니다.";
	private static final String ERROR_MESSAGE_NOT_EXIST_SOCIAL_TYPE = "존재하지 않은 OAuth2 요청입니다.";
	
	private final Map<SocialType, OAuth2Client> oAuth2ClientMap = new HashMap<>();

	public OAuth2Manager(List<OAuth2Client> oAuth2Clients) {
		oAuth2Clients.forEach(this::addClient);
	}

	private void addClient(OAuth2Client oAuth2Client) {
		SocialType socialType = oAuth2Client.getSocialType();
		if (oAuth2ClientMap.containsKey(socialType)) {
			throw new RuntimeException(ERROR_MESSAGE_ALREADY_EXIST_SOCIAL_TYPE);
		}

		oAuth2ClientMap.put(socialType, oAuth2Client);
	}

	public OAuth2Client getOAuth2Client(SocialType socialType) {
		return Optional.ofNullable(oAuth2ClientMap.get(socialType))
			.orElseThrow(() -> new RuntimeException(ERROR_MESSAGE_NOT_EXIST_SOCIAL_TYPE));
	}
}
