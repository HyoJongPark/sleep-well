package com.sleepwell.accommodation.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.dto.AccommodationCreateDto;
import com.sleepwell.accommodation.service.AccommodationService;
import com.sleepwell.auth.utils.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class AccommodationController {

	private final AccommodationService accommodationService;

	@PostMapping
	public ResponseEntity<Void> createAccommodation(@AuthUser Long hostId,
		@RequestBody @Valid AccommodationCreateDto dto) {
		Accommodation accommodation = accommodationService.createAccommodation(hostId, dto.toEntity());

		return ResponseEntity.created(URI.create("/rooms/" + accommodation.getId())).build();
	}
}
