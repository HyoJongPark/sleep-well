package com.sleepwell.reservation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.service.AccommodationService;
import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.domain.ReservationStatus;
import com.sleepwell.reservation.repository.ReservationRepository;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
	@Mock
	private UserService userService;

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private AccommodationService accommodationService;

	@InjectMocks
	private ReservationService reservationService;

	private Accommodation accommodation;

	private Reservation reservation;

	@BeforeEach
	void setup() {
		accommodation = mock(Accommodation.class);
		reservation = mock(Reservation.class);

		when(accommodationService.findById(any())).thenReturn(accommodation);
		when(userService.findById(any())).thenReturn(mock(User.class));
	}

	@Nested
	@DisplayName("예약 생성 테스트")
	class CreateReservation {
		@DisplayName("예약 일자 사이에 이미 예약이 있다면 예약 불가")
		@Test
		void createReservationWithInvalidCheckInDate() {
			//given
			when(reservationRepository.existsReservationInAccommodationThatDay(any(), any(), any(),
				eq(ReservationStatus.CANCELED))).thenReturn(true);

			//when - then
			assertThrows(BadRequestException.class, () -> reservationService.createReservation(reservation, 1L, 1L));
		}

		@DisplayName("최대 숙박 인원을 초과하면 예약 불가")
		@Test
		void createReservationWithInvalidNumberOfGuest() {
			//given
			int maximumNumberOfGuest = 10;

			when(reservationRepository.existsReservationInAccommodationThatDay(any(), any(), any(),
				eq(ReservationStatus.CANCELED))).thenReturn(false);
			when(accommodation.getMaximumNumberOfGuest()).thenReturn(maximumNumberOfGuest);
			when(reservation.getNumberOfGuest()).thenReturn(maximumNumberOfGuest + 1);

			//when - then
			assertThrows(BadRequestException.class, () -> reservationService.createReservation(reservation, 1L, 1L));
		}

		@DisplayName("정상 예약 생성 요청 시 예약 정보 반환")
		@Test
		void createReservationWithValidCheckInDate() {
			//given
			when(reservationRepository.existsReservationInAccommodationThatDay(any(), any(), any(),
				eq(ReservationStatus.CANCELED))).thenReturn(false);
			when(reservationRepository.save(any())).thenReturn(reservation);

			//when
			Reservation result = reservationService.createReservation(reservation, 1L, 1L);

			//then
			assertEquals(result, reservation);
		}
	}
}
