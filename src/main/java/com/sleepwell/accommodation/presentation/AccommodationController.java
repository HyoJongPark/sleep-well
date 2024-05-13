package com.sleepwell.accommodation.presentation;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.dto.AccommodationCreateDto;
import com.sleepwell.accommodation.dto.AccommodationSearchRequestDto;
import com.sleepwell.accommodation.dto.AccommodationSearchResponseDto;
import com.sleepwell.accommodation.dto.GetAccommodationResponseDto;
import com.sleepwell.accommodation.service.AccommodationService;
import com.sleepwell.auth.utils.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class AccommodationController {

	private final AccommodationService accommodationService;

	@GetMapping
	public ResponseEntity<List<AccommodationSearchResponseDto>> findAccommodation(
		@RequestBody AccommodationSearchRequestDto dto,
		@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "100") int pageSize
	) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<Accommodation> result = accommodationService.findAccommodation(dto, pageable);

		List<AccommodationSearchResponseDto> response = result.stream()
			.map(AccommodationSearchResponseDto::fromEntity)
			.collect(Collectors.toList());
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Void> createAccommodation(@AuthUser Long hostId,
		@RequestBody @Valid AccommodationCreateDto dto) {
		Accommodation accommodation = accommodationService.createAccommodation(hostId, dto.toEntity());

		return ResponseEntity.created(URI.create("/rooms/" + accommodation.getId())).build();
	}

	@GetMapping("/{accommodationId}")
	public ResponseEntity<GetAccommodationResponseDto> getAccommodation(@PathVariable Long accommodationId) {
		Accommodation accommodation = accommodationService.findById(accommodationId);

		return ResponseEntity.ok()
			.body(GetAccommodationResponseDto.fromEntity(accommodation));
	}
}
