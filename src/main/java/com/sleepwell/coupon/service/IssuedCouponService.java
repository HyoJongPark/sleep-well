package com.sleepwell.coupon.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.domain.ExpiryType;
import com.sleepwell.coupon.domain.IssuedCoupon;
import com.sleepwell.coupon.repository.IssuedCouponRepository;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssuedCouponService {

	private static final String ERROR_MESSAGE_COUPON_OUT_OF_STOCK = "재고가 소진된 쿠폰입니다.";
	private static final String ERROR_MESSAGE_ALREADY_ISSUED_COUPON = "이미 발급된 쿠폰입니다.";

	private final UserService userService;
	private final CouponService couponService;
	private final IssuedCouponRepository issuedCouponRepository;

	@Transactional
	public IssuedCoupon issueCoupon(Long userId, String couponCode) {
		Coupon coupon = couponService.findByCouponCode(couponCode);
		User user = userService.findById(userId);

		validateCouponStock(coupon);
		validateCouponAlreadyIssued(coupon, user);

		LocalDateTime expiredTime = calculateExpiredTime(coupon.getExpiryType(), coupon.getExpiryDateTime());

		IssuedCoupon issuedCoupon = new IssuedCoupon(expiredTime, user);
		coupon.updateCouponAmount(issuedCoupon);
		issuedCouponRepository.save(issuedCoupon);
		return issuedCoupon;
	}

	private LocalDateTime calculateExpiredTime(ExpiryType expiryType, LocalDateTime expiryDateTime) {
		return expiryType.calculateExpiredTime(expiryDateTime);
	}

	private static void validateCouponStock(Coupon coupon) {
		if (coupon.outOfStock()) {
			throw new RuntimeException(ERROR_MESSAGE_COUPON_OUT_OF_STOCK);
		}
	}

	private void validateCouponAlreadyIssued(Coupon coupon, User user) {
		if (issuedCouponRepository.existsByCouponAndIssuer(coupon, user)) {
			throw new RuntimeException(ERROR_MESSAGE_ALREADY_ISSUED_COUPON);
		}
	}
}
