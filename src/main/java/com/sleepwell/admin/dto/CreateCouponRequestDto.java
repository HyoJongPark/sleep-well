package com.sleepwell.admin.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.domain.DiscountType;
import com.sleepwell.coupon.domain.ExpiryType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCouponRequestDto(
	@NotBlank
	String title,

	@NotBlank
	String description,

	@NotNull
	DiscountType discountType,

	@Min(5)
	Integer discountAmount,

	@NotNull
	ExpiryType expiryType,

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	LocalDateTime expiryDateTime,

	@Min(1)
	Integer totalAmount,

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	LocalDateTime startDateTime,

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	LocalDateTime endDateTime
) {
	public Coupon toEntity() {
		return new Coupon(this.title, this.description, this.discountType, this.discountAmount, this.expiryType,
			this.expiryDateTime, this.totalAmount, this.startDateTime, this.endDateTime);
	}
}
