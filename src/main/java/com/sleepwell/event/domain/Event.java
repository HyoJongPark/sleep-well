package com.sleepwell.event.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sleepwell.coupon.domain.Coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Event {

	@Id
	@Column(name = "EVENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
	String title;

	@Column
	String description;

	@Column
	LocalDateTime startDateTime;

	@Column
	LocalDateTime endDateTime;

	@OneToMany
	List<Coupon> coupons = new ArrayList<>();
}
