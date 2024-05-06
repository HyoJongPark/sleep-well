package com.sleepwell.accommodation.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sleepwell.common.domain.BaseEntity;
import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation extends BaseEntity {

	@Id
	@Column(name = "ACCOMMODATION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String accommodationName;

	private int price;

	private String accommodationType;

	@Embedded
	private Address address;

	private LocalTime checkInTime;

	private LocalTime checkOutTime;

	private int maximumNumberOfGuest;

	@Lob
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User host;

	@OneToMany(mappedBy = "accommodation")
	private List<Reservation> reservations = new ArrayList<>();

	public Accommodation(String accommodationName, int price, String accommodationType, Address address,
		LocalTime checkInTime, LocalTime checkOutTime, int maximumNumberOfGuest, String description) {
		this.accommodationName = accommodationName;
		this.price = price;
		this.accommodationType = accommodationType;
		this.address = address;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.maximumNumberOfGuest = maximumNumberOfGuest;
		this.description = description;
	}

	public void setHost(User host) {
		this.host = host;
	}
}
