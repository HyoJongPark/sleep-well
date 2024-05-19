package com.sleepwell.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.sleepwell.coupon.domain.Coupon;

import jakarta.persistence.LockModeType;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	@Lock(LockModeType.OPTIMISTIC)
	Optional<Coupon> findByCouponCode(String couponCode);
}
