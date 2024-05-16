package com.sleepwell.reservation.presentation;

import java.net.URI;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sleepwell.auth.utils.AuthUser;
import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.dto.GetReservationResponseDto;
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
		Reservation reservation = reservationService.createReservation(dto.toEntity(), guestId, dto.accommodationId(),
			dto.issuedCoupon());

		return ResponseEntity
			.created(URI.create("/reservation/" + reservation.getId()))
			.build();
	}

	@GetMapping
	public ResponseEntity<Slice<GetReservationResponseDto>> findAllByGuestId(@AuthUser Long guestId,
		@RequestParam(defaultValue = "0") int pageNumber,
		@RequestParam(defaultValue = "100") int pageSize
	) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Slice<Reservation> result = reservationService.findAllByGuestId(guestId, pageable);

		Slice<GetReservationResponseDto> response = result.map(GetReservationResponseDto::fromEntity);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{reservationId}")
	public ResponseEntity<GetReservationResponseDto> findById(@AuthUser Long guestId,
		@PathVariable Long reservationId) {
		Reservation reservation = reservationService.findById(guestId, reservationId);

		return ResponseEntity.ok(GetReservationResponseDto.fromEntity(reservation));
	}
}
