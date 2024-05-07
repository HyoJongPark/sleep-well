package com.sleepwell.user.service;

import org.springframework.stereotype.Service;

import com.sleepwell.user.domain.User;
import com.sleepwell.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private static final String ERROR_MESSAGE_USER_NOT_FOUND = "존재하지 않는 사용자 입니다.";
	
	private final UserRepository userRepository;

	public User findById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException(ERROR_MESSAGE_USER_NOT_FOUND));
	}
}
