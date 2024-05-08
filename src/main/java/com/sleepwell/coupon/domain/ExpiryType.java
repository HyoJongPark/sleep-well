package com.sleepwell.coupon.domain;

import java.time.LocalDateTime;

/**
 * RELATIVE: 발급 일에 따라 상대적으로 만료 기간이 정해지는 쿠폰 타입
 * FIXED: 발급 일과 무관하게 만료 기간이 고정된 쿠폰 타입
 */
public enum ExpiryType {
	RELATIVE, FIXED;

	public LocalDateTime calculateExpiredTime(LocalDateTime expiryDateTime) {
		if (ExpiryType.RELATIVE.equals(this)) {
			return calculateRelativeExpiredTime(expiryDateTime);
		}
		return expiryDateTime;
	}

	private LocalDateTime calculateRelativeExpiredTime(LocalDateTime expiryDateTime) {
		return LocalDateTime.now()
			.plusDays(expiryDateTime.getDayOfMonth())
			.plusHours(expiryDateTime.getHour())
			.plusMinutes(expiryDateTime.getMinute())
			.plusSeconds(expiryDateTime.getSecond());
	}
}
