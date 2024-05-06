package com.sleepwell.auth.service;

import org.springframework.stereotype.Service;

import com.sleepwell.auth.dto.UserInfo;
import com.sleepwell.auth.utils.oauth.OAuth2Client;
import com.sleepwell.auth.utils.oauth.OAuth2Manager;
import com.sleepwell.user.domain.SocialType;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final OAuth2Manager oAuth2Manager;

	public User login(SocialType socialType, String accessToken) {
		OAuth2Client client = oAuth2Manager.getOAuth2Client(socialType);
		UserInfo userInfo = client.getUserInfo(accessToken);

		return userRepository.findBySocialIdAndSocialType(userInfo.socialId(), userInfo.socialType())
			.orElseGet(() -> signUp(userInfo));
	}

	private User signUp(UserInfo userInfo) {
		User user = userInfo.toEntity();

		return userRepository.save(user);
	}
}
