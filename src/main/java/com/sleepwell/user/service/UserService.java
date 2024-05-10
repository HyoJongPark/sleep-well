package com.sleepwell.user.service;

import org.springframework.stereotype.Service;

import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User findById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
	}
}
