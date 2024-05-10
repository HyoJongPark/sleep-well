package com.sleepwell.coupon.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sleepwell.auth.utils.AuthUser;
import com.sleepwell.coupon.domain.IssuedCoupon;
import com.sleepwell.coupon.service.IssuedCouponFacade;
import com.sleepwell.coupon.service.IssuedCouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class IssuedCouponController {

	private final IssuedCouponService issuedCouponService;
	private final IssuedCouponFacade issuedCouponFacade;

	@PostMapping("/{couponCode}")
	public ResponseEntity<Void> issueCoupon(@AuthUser Long userId, @PathVariable String couponCode) {
		IssuedCoupon issuedCoupon = issuedCouponFacade.issueCoupon(userId, couponCode);

		return ResponseEntity
			.created(URI.create("/coupon"))
			.build();
	}
}