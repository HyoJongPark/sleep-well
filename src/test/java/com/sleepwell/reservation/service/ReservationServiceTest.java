package com.sleepwell.reservation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

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
import com.sleepwell.common.error.exception.NotFoundException;
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

	private Reservation reservation;

	@BeforeEach
	void setup() {
		reservation = mock(Reservation.class);
	}

	@Nested
	@DisplayName("예약 생성 테스트")
	class CreateReservation {

		private Accommodation accommodation;

		@BeforeEach
		void setup() {
			accommodation = mock(Accommodation.class);

			when(accommodationService.findById(any())).thenReturn(accommodation);
			when(userService.findById(any())).thenReturn(mock(User.class));
		}

		@DisplayName("예약 일자 사이에 이미 예약이 있다면 예약 불가")
		@Test
		void createReservationWithInvalidCheckInDate() {
			//given
			when(reservationRepository.existsReservationInAccommodationThatDay(any(), any(), any(),
				eq(ReservationStatus.CANCELED))).thenReturn(true);

			//when - then
			assertThrows(BadRequestException.class, () -> reservationService.createReservation(reservation, 1L, 1L));

			verify(reservationRepository, times(1)).existsReservationInAccommodationThatDay(any(), any(), any(),
				eq(ReservationStatus.CANCELED));
			verify(accommodation, never()).getMaximumNumberOfGuest();
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

			verify(reservationRepository, times(1)).existsReservationInAccommodationThatDay(any(), any(), any(),
				eq(ReservationStatus.CANCELED));
			verify(accommodation, times(1)).getMaximumNumberOfGuest();
			verify(reservationRepository, never()).save(any());
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

			verify(reservationRepository, times(1)).existsReservationInAccommodationThatDay(any(), any(), any(),
				eq(ReservationStatus.CANCELED));
			verify(accommodation, times(1)).getMaximumNumberOfGuest();
			verify(reservationRepository, times(1)).save(any());
		}
	}

	@Nested
	@DisplayName("예약 단건 조회 테스트")
	class FindById {

		@DisplayName("존재하지 않는 예약 조회 시 예외 발생")
		@Test
		void findReservationWithNotExistReservationId() {
			//given
			when(reservationRepository.findById(any())).thenReturn(Optional.empty());

			//when - then
			assertThrows(NotFoundException.class, () -> reservationService.findById(1L, 1L));

			verify(reservationRepository, times(1)).findById(any());
			verify(reservation, never()).isGuest(any());
		}

		@DisplayName("예약자가 아닌 사용자가 예약 조회 시 예외 발생")
		@Test
		void findReservationWithInvalidGuestId() {
			//given
			when(reservationRepository.findById(any())).thenReturn(Optional.of(reservation));
			when(reservation.isGuest(any())).thenReturn(false);

			//when - then
			assertThrows(BadRequestException.class, () -> reservationService.findById(1L, 1L));

			verify(reservationRepository, times(1)).findById(any());
			verify(reservation, times(1)).isGuest(any());
		}

		@DisplayName("정상 예약 조회 요청 시 예약 정보 반환")
		@Test
		void findReservationWithValidRequest() {
			//given
			when(reservationRepository.findById(any())).thenReturn(Optional.of(reservation));
			when(reservation.isGuest(any())).thenReturn(true);

			//when
			Reservation result = reservationService.findById(1L, 1L);

			//then
			assertEquals(reservation, result);

			verify(reservationRepository, times(1)).findById(any());
			verify(reservation, times(1)).isGuest(any());
		}
	}
}
