package com.sleepwell.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sleepwell.coupon.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
