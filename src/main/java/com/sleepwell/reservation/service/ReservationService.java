package com.sleepwell.reservation.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.service.AccommodationService;
import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.common.error.exception.NotFoundException;
import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.domain.ReservationStatus;
import com.sleepwell.reservation.repository.ReservationRepository;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

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

	public Slice<Reservation> findAllByGuestId(Long guestId, Pageable pageable) {
		return reservationRepository.findAllByGuestId(guestId, pageable);
	}

	public Reservation findById(Long guestId, Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXIST_RESERVATION));

		validateGuestId(reservation, guestId);
		return reservation;
	}

	private void validateAlreadyHasReservation(Reservation reservation, Accommodation accommodation) {
		if (reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
			reservation.getCheckInDate(), reservation.getCheckOutDate(), ReservationStatus.CANCELED)) {
			throw new BadRequestException(ErrorCode.INVALID_RESERVATION_DATE);
		}
	}

	private void validateNumberOfGuest(Reservation reservation, Accommodation accommodation) {
		if (accommodation.getMaximumNumberOfGuest() < reservation.getNumberOfGuest()) {
			throw new BadRequestException(ErrorCode.INVALID_NUMBER_OF_GUEST);
		}
	}

	private void validateGuestId(Reservation reservation, Long guestId) {
		if (!reservation.isGuest(guestId)) {
			throw new BadRequestException(ErrorCode.INVALID_RESERVATION_GUEST);
		}
	}
}
