package com.sleepwell.accommodation.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;

@Builder
public record AccommodationSearchRequestDto(
	String accommodationName,

	String accommodationType,

	String streetAddress,

	String detailAddress,

	String postcode,

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate checkInDate,

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate checkOutDate,

	Integer minPrice,

	Integer maxPrice,

	Integer numberOfGuest
) {
}
