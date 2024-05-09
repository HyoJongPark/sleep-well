package com.sleepwell.coupon.service;

import org.springframework.stereotype.Service;

import com.sleepwell.coupon.domain.IssuedCoupon;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssuedCouponFacade {

	private final IssuedCouponService issuedCouponService;

	synchronized public IssuedCoupon issueCoupon(Long userId, String couponCode) {
		return issuedCouponService.issueCoupon(userId, couponCode);
	}
}
