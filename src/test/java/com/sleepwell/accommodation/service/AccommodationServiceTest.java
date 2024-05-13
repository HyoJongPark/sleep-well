package com.sleepwell.accommodation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.repository.AccommodationRepository;
import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

	@Mock
	AccommodationRepository accommodationRepository;

	@Mock
	UserService userService;

	@InjectMocks
	AccommodationService accommodationService;

	@Nested
	@DisplayName("숙소 조회 테스트")
	class FindById {
		@DisplayName("존재하지 않는 숙소 조회 시 예외 발생")
		@Test
		void findAccommodationWithInvalidAccommodationId() {
			//given
			when(accommodationRepository.findById(any())).thenReturn(Optional.empty());

			//then
			assertThrows(BadRequestException.class, () -> accommodationService.findById(1L));
		}

		@DisplayName("유효한 숙소 조회 시 숙소 반환")
		@Test
		void findAccommodationWithValidAccommodationId() {
			//given
			Accommodation mockAccommodation = mock(Accommodation.class);
			when(accommodationRepository.findById(any())).thenReturn(Optional.of(mockAccommodation));

			//when
			Accommodation findAccommodation = accommodationService.findById(1L);

			//then
			assertEquals(mockAccommodation, findAccommodation);
		}
	}

	@Nested
	@DisplayName("숙소 등록 테스트")
	class CreateAccommodation {
		@DisplayName("유효한 숙소 등록 요청 시 생성된 숙소 반환")
		@Test
		void createAccommodationWithValidRequest() {
			//given
			Accommodation accommodation = mock(Accommodation.class);

			when(userService.findById(any())).thenReturn(mock(User.class));
			when(accommodationRepository.save(any())).thenReturn(accommodation);

			//when
			Accommodation createdAccommodation = accommodationService.createAccommodation(1L, accommodation);

			//then
			assertEquals(accommodation, createdAccommodation);
		}
	}
}
