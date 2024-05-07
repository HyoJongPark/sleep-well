package com.sleepwell.reservation.domain;

import java.time.LocalDate;

import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Reservation {

	@Id
	@Column(name = "RESERVATION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate checkInDate;

	private LocalDate checkOutDate;

	private LocalDate reservedDate;

	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus;

	private int numberOfGuest;

	private int amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User guest;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOMMODATION_ID")
	private Accommodation accommodation;

	public Reservation(LocalDate checkInDate, LocalDate checkOutDate, LocalDate reservedDate,
		ReservationStatus reservationStatus, int numberOfGuest, int amount) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.reservedDate = reservedDate;
		this.reservationStatus = reservationStatus;
		this.numberOfGuest = numberOfGuest;
		this.amount = amount;
	}
}
