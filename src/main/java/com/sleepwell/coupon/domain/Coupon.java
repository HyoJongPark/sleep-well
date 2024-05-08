package com.sleepwell.coupon.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sleepwell.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Coupon extends BaseEntity {

	@Id
	@Column(name = "COUPON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
	String title;

	@Column
	String description;

	@Enumerated(EnumType.STRING)
	DiscountType discountType;

	@Column
	Integer discountAmount;

	@Enumerated(EnumType.STRING)
	ExpiryType expiryType;

	@Column
	LocalDateTime expiryDateTime;

	@Column
	Integer totalAmount;

	@Column
	Integer issuedAmount;

	@Column
	LocalDateTime startDateTime;

	@Column
	LocalDateTime endDateTime;

	@OneToMany
	List<IssuedCoupon> issuedCoupons = new ArrayList<>();
}
