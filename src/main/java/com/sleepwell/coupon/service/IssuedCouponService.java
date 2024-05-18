package com.sleepwell.coupon.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.domain.ExpiryType;
import com.sleepwell.coupon.domain.IssuedCoupon;
import com.sleepwell.coupon.repository.IssuedCouponRepository;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IssuedCouponService {

	private final UserService userService;
	private final CouponService couponService;
	private final IssuedCouponRepository issuedCouponRepository;

	public IssuedCoupon issueCoupon(Long userId, String couponCode) {
		Coupon coupon = couponService.findByCouponCode(couponCode);
		User user = userService.findById(userId);

		validateCouponStock(coupon);
		// validateCouponAlreadyIssued(coupon, user);

		LocalDateTime expiredTime = calculateExpiredTime(coupon.getExpiryType(), coupon.getExpiryDateTime());

		IssuedCoupon issuedCoupon = new IssuedCoupon(expiredTime, user);
		coupon.updateCouponAmount(issuedCoupon);
		return issuedCouponRepository.save(issuedCoupon);
	}

	public IssuedCoupon findById(Long issuedCouponId) {
		return issuedCouponRepository.findById(issuedCouponId)
			.orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXIST_ISSUED_COUPON));
	}

	private LocalDateTime calculateExpiredTime(ExpiryType expiryType, LocalDateTime expiryDateTime) {
		return expiryType.calculateExpiredTime(expiryDateTime);
	}

	private static void validateCouponStock(Coupon coupon) {
		if (coupon.outOfStock()) {
			throw new BadRequestException(ErrorCode.COUPON_OUT_OF_STOCK);
		}
	}

	private void validateCouponAlreadyIssued(Coupon coupon, User user) {
		if (issuedCouponRepository.existsByCouponAndIssuer(coupon, user)) {
			throw new BadRequestException(ErrorCode.ALREADY_ISSUED_COUPON);
		}
	}
}
