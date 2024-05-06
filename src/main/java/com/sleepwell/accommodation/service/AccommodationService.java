package com.sleepwell.accommodation.service;

import org.springframework.stereotype.Service;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.repository.AccommodationRepository;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccommodationService {

	private final UserRepository userRepository;
	private final AccommodationRepository accommodationRepository;

	public Accommodation createAccommodation(Long hostId, Accommodation accommodation) {
		User host = userRepository.findById(hostId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));
		accommodation.setHost(host);
		return accommodationRepository.save(accommodation);
	}
}
