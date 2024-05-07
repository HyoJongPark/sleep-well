package com.sleepwell.reservation.service;

import org.springframework.stereotype.Service;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.service.AccommodationService;
import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.domain.ReservationStatus;
import com.sleepwell.reservation.repository.ReservationRepository;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private static final String INVALID_RESERVATION_DATE = "해당 일자에 예약이 불가합니다.";
	private static final String INVALID_NUMBER_OF_GUEST = "예약 가능 인원을 초과했습니다.";

	private final UserService userService;
	private final AccommodationService accommodationService;
	private final ReservationRepository reservationRepository;

	public Reservation createReservation(Reservation reservation, Long guestId, Long accommodationId) {
		User guest = userService.findById(guestId);
		Accommodation accommodation = accommodationService.findById(accommodationId);

		validateAlreadyHasReservation(reservation, accommodation);
		validateNumberOfGuest(reservation, accommodation);

		reservation.updateGuestAndAccommodation(guest, accommodation);
		return reservationRepository.save(reservation);
	}

	private void validateAlreadyHasReservation(Reservation reservation, Accommodation accommodation) {
		if (reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
			reservation.getCheckInDate(), reservation.getCheckOutDate(), ReservationStatus.CANCELED)) {
			throw new RuntimeException(INVALID_RESERVATION_DATE);
		}
	}

	private void validateNumberOfGuest(Reservation reservation, Accommodation accommodation) {
		if (accommodation.getMaximumNumberOfGuest() < reservation.getNumberOfGuest()) {
			throw new RuntimeException(INVALID_NUMBER_OF_GUEST);
		}
	}
}
