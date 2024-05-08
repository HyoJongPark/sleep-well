package com.sleepwell.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.domain.IssuedCoupon;
import com.sleepwell.user.domain.User;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {
	boolean existsByCouponAndIssuer(Coupon coupon, User user);
}
