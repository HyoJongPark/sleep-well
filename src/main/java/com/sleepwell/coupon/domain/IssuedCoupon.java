package com.sleepwell.coupon.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import com.sleepwell.common.domain.BaseEntity;
import com.sleepwell.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuedCoupon extends BaseEntity {

	@Id
	@Column(name = "ISSUED_COUPON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Enumerated(EnumType.STRING)
	CouponStatus couponStatus = CouponStatus.ISSUED;

	@Column
	LocalDateTime expiredDate;

	@ManyToOne
	User issuer;

	@ManyToOne
	Coupon coupon;

	public IssuedCoupon(LocalDateTime expiredDate, User issuer) {
		this.expiredDate = expiredDate;
		this.issuer = issuer;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public boolean isIssuer(User issuer) {
		return Objects.equals(this.issuer, issuer);
	}

	public boolean alreadyUseCoupon() {
		return couponStatus == CouponStatus.USED;
	}

	public int getDiscountPrice(int amount) {
		DiscountType discountType = coupon.getDiscountType();
		Integer discountAmount = coupon.getDiscountAmount();

		if (discountType == DiscountType.PERCENT) {
			return amount * discountAmount / 100;
		}
		return Math.max(amount - discountAmount, 0);
	}
}
