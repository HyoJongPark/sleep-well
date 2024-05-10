package com.sleepwell.auth.utils.oauth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.common.error.exception.InternalServerException;
import com.sleepwell.user.domain.SocialType;

@Component
public class OAuth2Manager {

	private final Map<SocialType, OAuth2Client> oAuth2ClientMap = new HashMap<>();

	public OAuth2Manager(List<OAuth2Client> oAuth2Clients) {
		oAuth2Clients.forEach(this::addClient);
	}

	private void addClient(OAuth2Client oAuth2Client) {
		SocialType socialType = oAuth2Client.getSocialType();
		if (oAuth2ClientMap.containsKey(socialType)) {
			throw new InternalServerException(ErrorCode.ALREADY_EXIST_SOCIAL_TYPE);
		}

		oAuth2ClientMap.put(socialType, oAuth2Client);
	}

	public OAuth2Client getOAuth2Client(SocialType socialType) {
		return Optional.ofNullable(oAuth2ClientMap.get(socialType))
			.orElseThrow(() -> new InternalServerException(ErrorCode.NOT_EXIST_SOCIAL_TYPE));
	}
}
