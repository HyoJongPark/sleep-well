package com.sleepwell.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sleepwell.user.domain.User;
import com.sleepwell.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	@Nested
	@DisplayName("사용자 조회 테스트")
	class FindById {
		@Test
		@DisplayName("존재하지 않는 사용자 조회 시 에러 발생")
		void findUserWithNotExistUserId() {
			//given
			when(userRepository.findById(any())).thenReturn(Optional.empty());

			//when - then
			assertThrows(RuntimeException.class, () -> userService.findById(1L));
		}

		@Test
		@DisplayName("정상 조회 요청 시 사용자 정보 반환")
		void findUserWithValidUserId() {
			//given
			User user = mock(User.class);
			when(userRepository.findById(any())).thenReturn(Optional.of(user));

			//when
			User result = userService.findById(1L);

			//when - then
			assertEquals(user, result);
		}
	}
}
