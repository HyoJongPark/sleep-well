package com.sleepwell.reservation.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.domain.ReservationStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationRequestDto(
	@NotNull
	Long accommodationId,

	@NotNull
	Long guestId,

	@NotBlank
	String accommodationName,

	int amount,

	@NotBlank
	String accommodationType,

	@NotBlank
	String location,

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate checkInDate,

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate checkOutDate,

	@Min(1)
	int numberOfGuest
) {
	public Reservation toEntity() {
		return new Reservation(checkInDate, checkOutDate, ReservationStatus.BEFORE_PAYED, numberOfGuest, amount);
	}
}
