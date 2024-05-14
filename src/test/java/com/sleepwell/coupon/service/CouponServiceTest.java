package com.sleepwell.coupon.service;

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

import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.repository.CouponRepository;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

	@Mock
	CouponRepository couponRepository;

	@InjectMocks
	CouponService couponService;

	Coupon coupon;

	@BeforeEach
	void setup() {
		coupon = mock(Coupon.class);
	}

	@Nested
	@DisplayName("쿠폰 단건 조회 테스트")
	class FindByCouponCode {

		@DisplayName("존재하지 않는 쿠폰 코드로 조회 시 예외 발생")
		@Test
		void findCouponWithNotExistCouponCode() {
			//given
			String couponCode = "NOT_EXIST_COUPON_CODE";

			when(couponRepository.findByCouponCode(any())).thenReturn(Optional.empty());

			//when-then
			assertThrows(BadRequestException.class, () -> couponService.findByCouponCode(couponCode));
		}

		@DisplayName("유효한 쿠폰 코드로 조회 시 예외 발생")
		@Test
		void findCouponWithValidCouponCode() {
			//given
			String couponCode = "COUPON_CODE";

			when(couponRepository.findByCouponCode(any())).thenReturn(Optional.of(coupon));

			//when
			Coupon result = couponService.findByCouponCode(couponCode);

			//then
			assertEquals(coupon, result);
		}
	}
}
