package com.sleepwell.coupon.domain;

import java.time.LocalDateTime;

import com.sleepwell.common.domain.BaseEntity;
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
public class IssuedCoupon extends BaseEntity {

	@Id
	@Column(name = "ISSUED_COUPON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Enumerated(EnumType.STRING)
	CouponStatus couponStatus;

	@Column
	LocalDateTime expiredDate;

	@OneToOne
	User issuer;
}
