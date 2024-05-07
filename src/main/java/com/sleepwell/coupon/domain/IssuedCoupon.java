package com.sleepwell.coupon.domain;

import com.sleepwell.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class IssuedCoupon {

	@Id
	@Column(name = "ISSUED_COUPON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Enumerated(EnumType.STRING)
	CouponStatus couponStatus;

	@OneToOne
	User issuer;
}
