package com.sleepwell.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sleepwell.coupon.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	Optional<Coupon> findByCouponCode(String couponCode);
}
