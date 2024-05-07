package com.sleepwell.reservation.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sleepwell.auth.utils.AuthUser;
import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.dto.ReservationRequestDto;
import com.sleepwell.reservation.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public ResponseEntity<Void> createReservation(@AuthUser Long guestId,
		@RequestBody @Valid ReservationRequestDto dto) {
		Reservation reservation = reservationService.createReservation(dto.toEntity(), guestId, dto.accommodationId());

		return ResponseEntity
			.created(URI.create("/reservation/" + reservation.getId()))
			.build();
	}
}
