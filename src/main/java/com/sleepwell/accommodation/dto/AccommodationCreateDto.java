package com.sleepwell.accommodation.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.domain.Address;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccommodationCreateDto(
	@NotBlank
	String accommodationName,

	@Min(1)
	int price,

	@NotBlank
	String accommodationType,

	@NotBlank
	String streetAddress,

	@NotBlank
	String detailAddress,

	@NotBlank
	String postcode,

	@NotNull
	@DateTimeFormat(pattern = "kk:mm")
	LocalTime checkInTime,

	@NotNull
	@DateTimeFormat(pattern = "kk:mm")
	LocalTime checkOutTime,

	@Min(1)
	int maximumNumberOfGuest,

	String description
) {

	public Accommodation toEntity() {
		return new Accommodation(accommodationName, price, accommodationType,
			new Address(streetAddress, detailAddress, postcode),
			checkInTime, checkOutTime, maximumNumberOfGuest, description);
	}
}
