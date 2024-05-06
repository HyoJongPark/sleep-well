package com.sleepwell.accommodation.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	private String streetAddress;

	private String detailAddress;

	private String postcode;

	public Address(String streetAddress, String detailAddress, String postcode) {
		this.streetAddress = streetAddress;
		this.detailAddress = detailAddress;
		this.postcode = postcode;
	}
}
