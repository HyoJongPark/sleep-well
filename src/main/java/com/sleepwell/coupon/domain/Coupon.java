package com.sleepwell.coupon.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Coupon {

	@Id
	@Column(name = "COUPON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Enumerated(value = EnumType.STRING)
	CouponType couponType;

	@Column
	Integer discountAmount;

	@Column
	int totalAmount;

	@Column
	int issuedAmount;

	@OneToMany(mappedBy = "issued_coupon")
	List<IssuedCoupon> issuedCoupons = new ArrayList<>();
}
