package com.sleepwell.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sleepwell.accommodation.domain.Address;
import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.domain.ReservationStatus;

import lombok.Builder;

@Builder
public record GetReservationResponseDto(
	LocalDate checkInDate,
	LocalDate checkOutDate,
	LocalTime checkInTime,
	LocalTime checkOutTime,
	ReservationStatus reservationStatus,
	int numberOfGuest,
	String accommodationName,
	String description,
	Address address
) {

	public static GetReservationResponseDto fromEntity(Reservation reservation) {
		return GetReservationResponseDto.builder()
			.checkInDate(reservation.getCheckInDate())
			.checkOutDate(reservation.getCheckOutDate())
			.checkInTime(reservation.getAccommodation().getCheckInTime())
			.checkOutTime(reservation.getAccommodation().getCheckOutTime())
			.reservationStatus(reservation.getReservationStatus())
			.numberOfGuest(reservation.getNumberOfGuest())
			.accommodationName(reservation.getAccommodation().getAccommodationName())
			.description(reservation.getAccommodation().getDescription())
			.address(reservation.getAccommodation().getAddress())
			.build();
	}
}
