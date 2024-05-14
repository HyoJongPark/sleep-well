package com.sleepwell.coupon.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

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
import com.sleepwell.coupon.domain.ExpiryType;
import com.sleepwell.coupon.domain.IssuedCoupon;
import com.sleepwell.coupon.repository.IssuedCouponRepository;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class IssuedCouponServiceTest {

	@Mock
	UserService userService;

	@Mock
	CouponService couponService;

	@Mock
	IssuedCouponRepository issuedCouponRepository;

	@InjectMocks
	IssuedCouponService issuedCouponService;

	IssuedCoupon issuedCoupon;

	Coupon coupon;

	User user;

	@BeforeEach
	void setup() {
		issuedCoupon = mock(IssuedCoupon.class);
		coupon = mock(Coupon.class);
		user = mock(User.class);
	}

	@Nested
	@DisplayName("쿠폰 발급 테스트")
	class IssueCoupon {

		@BeforeEach
		void setup() {
			when(couponService.findByCouponCode(any())).thenReturn(coupon);
			when(userService.findById(any())).thenReturn(user);
		}

		@DisplayName("재고가 없는 쿠폰 발급 시 예외 발생")
		@Test
		void issueCouponWithOutOfStockCoupon() {
			//given
			String couponCode = "COUPON_CODE";

			when(coupon.outOfStock()).thenReturn(true);

			//when - then
			assertThrows(BadRequestException.class, () -> issuedCouponService.issueCoupon(1L, couponCode));

			verify(coupon, times(1)).outOfStock();
			verify(issuedCouponRepository, never()).existsByCouponAndIssuer(any(), any());
		}

		@DisplayName("이미 발급한 쿠폰 발급 요청 시 예외 발생")
		@Test
		void issueCouponWithAlreadyExistCouponId() {
			//given
			String couponCode = "COUPON_CODE";

			when(coupon.outOfStock()).thenReturn(false);
			when(issuedCouponRepository.existsByCouponAndIssuer(any(), any())).thenReturn(true);

			//when - then
			assertThrows(BadRequestException.class, () -> issuedCouponService.issueCoupon(1L, couponCode));

			verify(coupon, times(1)).outOfStock();
			verify(issuedCouponRepository, times(1)).existsByCouponAndIssuer(any(), any());
			verify(issuedCouponRepository, never()).save(any());
		}

		@DisplayName("정상 쿠폰 발급 요청 시 발급 쿠폰 반환")
		@Test
		void issueCouponWithValidRequest() {
			//given
			String couponCode = "COUPON_CODE";

			when(coupon.outOfStock()).thenReturn(false);
			when(coupon.getExpiryType()).thenReturn(ExpiryType.FIXED);
			when(coupon.getExpiryDateTime()).thenReturn(LocalDateTime.now().plusDays(1));
			when(issuedCouponRepository.existsByCouponAndIssuer(any(), any())).thenReturn(false);
			when(issuedCouponRepository.save(any())).thenReturn(issuedCoupon);

			//when
			IssuedCoupon result = issuedCouponService.issueCoupon(1L, couponCode);

			//then
			assertEquals(issuedCoupon, result);
			
			verify(coupon, times(1)).outOfStock();
			verify(issuedCouponRepository, times(1)).existsByCouponAndIssuer(any(), any());
			verify(issuedCouponRepository, times(1)).save(any());
		}
	}
}
