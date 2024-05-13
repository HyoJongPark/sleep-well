package com.sleepwell.reservation.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.domain.Address;
import com.sleepwell.accommodation.repository.AccommodationRepository;
import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.domain.ReservationStatus;
import com.sleepwell.user.domain.SocialType;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccommodationRepository accommodationRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@DisplayName("숙박 일자하려는 날짜의")
	@Nested
	class CheckReservationDate {

		private Accommodation accommodation;

		private LocalDate checkInDate;

		private LocalDate checkOutDate;

		@BeforeEach
		void setUp() {
			//given
			checkInDate = LocalDate.of(2023, 6, 8);
			checkOutDate = LocalDate.of(2023, 6, 10);

			User member = userRepository.save(new User("socialId", SocialType.GOOGLE, "nickname", "profileImage"));
			Reservation reservation = reservationRepository.save(
				new Reservation(checkInDate, checkOutDate, ReservationStatus.RESERVED, 1, 1000));
			accommodation = accommodationRepository.save(
				new Accommodation("숙소 이름", 1000, "HOTEL", mock(Address.class), LocalTime.of(11, 0), LocalTime.of(15, 0),
					10, "세부사항"));
			reservation.updateGuestAndAccommodation(member, accommodation);
		}

		@DisplayName("사이에 체크인 하는 예약이 존재하면 true 반환")
		@Test
		void hasCheckInDateBetweenReservationDate() {
			//when
			boolean result = reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
				checkInDate.plusDays(1), checkOutDate.plusDays(1), ReservationStatus.CANCELED);

			//then
			assertTrue(result);
		}

		@DisplayName("사이에 체크아웃 하는 예약이 존재하면 true 반환")
		@Test
		void hasCheckOutDateBetweenReservationDate() {
			//when
			boolean result = reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
				checkInDate.minusDays(1), checkOutDate.minusDays(1), ReservationStatus.CANCELED);

			//then
			assertTrue(result);
		}

		@DisplayName("사이에 예약이 존재하지 않으면 false 반환")
		@Test
		void hasNotAnyReservationBetweenReservationDate() {
			//when
			boolean result = reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
				checkInDate.minusDays(20), checkOutDate.minusDays(20), ReservationStatus.CANCELED);

			//then
			assertFalse(result);
		}

		@DisplayName("체크인과 같은 날에 체크인하는 예약이 존재하면 true 반환")
		@Test
		void isEqualCheckInDateWithOriginCheckInDate() {
			//when
			boolean result = reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
				checkInDate, checkOutDate.plusDays(1), ReservationStatus.CANCELED);

			//then
			assertTrue(result);
		}

		@DisplayName("체크인과 같은 날에 체크아웃하는 예약이 존재하면 false 반환")
		@Test
		void isEqualCheckOutDateWithOriginCheckInDate() {
			//when
			boolean result = reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
				checkInDate.minusDays(1), checkInDate, ReservationStatus.CANCELED);

			//then
			assertFalse(result);
		}

		@DisplayName("체크아웃과 같은 날에 체크인하는 예약이 존재하면 false 반환")
		@Test
		void isEqualCheckInDateWithOriginCheckOutDate() {
			//when
			boolean result = reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
				checkOutDate, checkOutDate.plusDays(1), ReservationStatus.CANCELED);

			//then
			assertFalse(result);
		}

		@DisplayName("체크아웃과 같은 날에 체크아웃하는 예약이 존재하면 true 반환")
		@Test
		void isEqualCheckOutDateWithOriginCheckOutDate() {
			//when
			boolean result = reservationRepository.existsReservationInAccommodationThatDay(accommodation.getId(),
				checkInDate.minusDays(1), checkOutDate, ReservationStatus.CANCELED);

			//then
			assertTrue(result);
		}
	}
}
