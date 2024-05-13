package com.sleepwell.accommodation.dto;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.domain.Address;

import lombok.Builder;

@Builder
public record AccommodationSearchResponseDto(
	String accommodationName,

	String accommodationType,

	int price,

	Address address,

	int maximumNumberOfGuest

) {
	public static AccommodationSearchResponseDto fromEntity(Accommodation accommodation) {
		return AccommodationSearchResponseDto.builder()
			.accommodationName(accommodation.getAccommodationName())
			.accommodationType(accommodation.getAccommodationType())
			.price(accommodation.getPrice())
			.address(accommodation.getAddress())
			.maximumNumberOfGuest(accommodation.getMaximumNumberOfGuest())
			.build();
	}
}
