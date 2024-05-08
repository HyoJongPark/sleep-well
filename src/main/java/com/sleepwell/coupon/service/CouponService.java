package com.sleepwell.coupon.service;

import org.springframework.stereotype.Service;

import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	private static final String ERROR_MESSAGE_INVALID_COUPON_CODE = "유효하지 않는 쿠폰 코드입니다.";
	
	private final CouponRepository couponRepository;

	public Coupon createCoupon(Coupon coupon) {
		return couponRepository.save(coupon);
	}

	public Coupon findByCouponCode(String couponCode) {
		return couponRepository.findByCouponCode(couponCode)
			.orElseThrow(() -> new RuntimeException(ERROR_MESSAGE_INVALID_COUPON_CODE));
	}
}
