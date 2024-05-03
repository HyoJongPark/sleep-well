package com.sleepwell.accommodation.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {

	private String streetAddress;

	private String detailAddress;

	private String postcode;
}
