package com.sleepwell.coupon.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sleepwell.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

	@Id
	@Column(name = "COUPON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
	String title;

	@Column
	String description;

	@Column
	String couponCode;

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
	Integer issuedAmount = 0;

	@Column
	LocalDateTime startDateTime;

	@Column
	LocalDateTime endDateTime;

	@OneToMany
	List<IssuedCoupon> issuedCoupons = new ArrayList<>();

	public Coupon(String title, String description, String couponCode, DiscountType discountType,
		Integer discountAmount, ExpiryType expiryType, LocalDateTime expiryDateTime, Integer totalAmount,
		LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.title = title;
		this.description = description;
		this.couponCode = couponCode;
		this.discountType = discountType;
		this.discountAmount = discountAmount;
		this.expiryType = expiryType;
		this.expiryDateTime = expiryDateTime;
		this.totalAmount = totalAmount;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public void updateCouponAmount(IssuedCoupon issuedCoupon) {
		this.issuedAmount++;

		issuedCoupon.setCoupon(this);
		this.issuedCoupons.add(issuedCoupon);
	}

	public boolean outOfStock() {
		return Objects.equals(this.totalAmount, this.issuedAmount);
	}
}
