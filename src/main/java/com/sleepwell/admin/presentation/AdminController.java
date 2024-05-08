package com.sleepwell.admin.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sleepwell.admin.dto.CreateCouponRequestDto;
import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.service.CouponService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final CouponService couponService;

	@PostMapping("/coupon")
	public ResponseEntity<Void> createCoupon(@RequestBody @Valid CreateCouponRequestDto dto) {
		Coupon coupon = couponService.createCoupon(dto.toEntity());

		return ResponseEntity
			.created(URI.create("/admin/coupon/" + coupon.getId()))
			.build();
	}
}
