package com.sleepwell.coupon.service;

import org.springframework.stereotype.Service;

import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	public Coupon createCoupon(Coupon coupon) {
		return couponRepository.save(coupon);
	}

	public Coupon findByCouponCode(String couponCode) {
		return couponRepository.findByCouponCode(couponCode)
			.orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_COUPON_CODE));
	}
}
