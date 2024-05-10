package com.sleepwell.accommodation.service;

import org.springframework.stereotype.Service;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.repository.AccommodationRepository;
import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccommodationService {

	private final UserService userService;
	private final AccommodationRepository accommodationRepository;

	public Accommodation createAccommodation(Long hostId, Accommodation accommodation) {
		User host = userService.findById(hostId);
		accommodation.setHost(host);

		return accommodationRepository.save(accommodation);
	}

	public Accommodation findById(Long accommodationId) {
		return accommodationRepository.findById(accommodationId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.ROOM_NOT_FOUND));
	}
}
