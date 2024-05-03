package com.sleepwell.auth.utils;

import java.util.Optional;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sleepwell.auth.dto.GoogleUserInfo;
import com.sleepwell.auth.dto.UserInfo;
import com.sleepwell.user.domain.SocialType;

@Component
public class GoogleOAuth2Client implements OAuth2Client {

	private static final String ERROR_MESSAGE_INVALID_LOGIN_REQUEST = "유효하지 않은 로그인 요청입니다.";
	private static final String GOOGLE_USER_INFO_API_URL = "https://www.googleapis.com/userinfo/v2/me";

	private final RestTemplate restTemplate;

	public GoogleOAuth2Client(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public SocialType getSocialType() {
		return SocialType.GOOGLE;
	}

	@Override
	public UserInfo getUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		GoogleUserInfo googleUserInfo = restTemplate.exchange(GOOGLE_USER_INFO_API_URL, HttpMethod.GET,
				new HttpEntity<>(headers), GoogleUserInfo.class)
			.getBody();
		return Optional.ofNullable(googleUserInfo)
			.orElseThrow(() -> new RuntimeException(ERROR_MESSAGE_INVALID_LOGIN_REQUEST))
			.toUserInfo();
	}
}
