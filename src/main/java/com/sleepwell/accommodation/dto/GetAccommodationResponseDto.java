package com.sleepwell.accommodation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.domain.Address;

import lombok.Builder;

@Builder
public record GetAccommodationResponseDto(
	Long accommodationId,

	String accommodationName,

	int price,

	String accommodationType,

	Address address,

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	LocalDate checkInDate,

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	LocalDate checkOutDate,

	@DateTimeFormat(pattern = "hh:mm")
	LocalTime checkInTime,

	@DateTimeFormat(pattern = "hh:mm")
	LocalTime checkOutTime,

	Integer maximumNumberOfGuest,

	String description
) {

	public static GetAccommodationResponseDto fromEntity(Accommodation accommodation) {
		return GetAccommodationResponseDto.builder()
			.accommodationId(accommodation.getId())
			.accommodationName(accommodation.getAccommodationName())
			.price(accommodation.getPrice())
			.accommodationType(accommodation.getAccommodationType())
			.address(accommodation.getAddress())
			.checkInTime(accommodation.getCheckInTime())
			.checkOutTime(accommodation.getCheckOutTime())
			.maximumNumberOfGuest(accommodation.getMaximumNumberOfGuest())
			.description(accommodation.getDescription())
			.build();
	}
}
