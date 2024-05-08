package com.sleepwell.coupon.domain;

/**
 * RELATIVE: 발급 일에 따라 상대적으로 만료 기간이 정해지는 쿠폰 타입
 * FIXED: 발급 일과 무관하게 만료 기간이 고정된 쿠폰 타입
 */
public enum ExpiryType {
	RELATIVE, FIXED
}
