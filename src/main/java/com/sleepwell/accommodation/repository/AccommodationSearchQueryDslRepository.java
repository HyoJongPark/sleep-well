package com.sleepwell.accommodation.repository;

import static com.sleepwell.accommodation.domain.QAccommodation.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sleepwell.accommodation.domain.Accommodation;
import com.sleepwell.accommodation.dto.AccommodationSearchRequestDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccommodationSearchQueryDslRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public List<Accommodation> findAllByAccommodationSearchDto(AccommodationSearchRequestDto dto, Pageable pageable) {
		return jpaQueryFactory
			.selectFrom(accommodation)
			.where(
				idGoe(pageable),
				nameEq(dto.accommodationName()),
				typeEq(dto.accommodationType()),
				streetAddressEq(dto.streetAddress()),
				detailAddressEq(dto.detailAddress()),
				postcodeEq(dto.postcode()),
				notExistsReservationBetweenDates(dto.checkInDate(), dto.checkOutDate()),
				priceBetween(dto.minPrice(), dto.maxPrice()),
				numberOfGuestGoe(dto.numberOfGuest()))
			.limit(pageable.getPageSize())
			.fetch();
	}

	private static BooleanExpression idGoe(Pageable pageable) {
		return accommodation.id.goe(pageable.getPageNumber());
	}

	private BooleanExpression nameEq(String accommodationName) {
		return accommodationName != null ? accommodation.accommodationName.eq(accommodationName) : null;
	}

	private BooleanExpression typeEq(String accommodationType) {
		return accommodationType != null ? accommodation.accommodationType.eq(accommodationType) : null;
	}

	private BooleanExpression streetAddressEq(String streetAddress) {
		return streetAddress != null ? accommodation.address.streetAddress.eq(streetAddress) : null;
	}

	private BooleanExpression detailAddressEq(String detailAddress) {
		return detailAddress != null ? accommodation.address.detailAddress.eq(detailAddress) : null;
	}

	private BooleanExpression postcodeEq(String postcode) {
		return postcode != null ? accommodation.address.postcode.eq(postcode) : null;
	}

	private BooleanExpression notExistsReservationBetweenDates(LocalDate checkInDate, LocalDate checkOutDate) {
		if (checkInDate == null && checkOutDate == null) {
			return null;
		} else if (checkInDate == null) {
			checkInDate = checkOutDate.minusDays(1);
		} else if (checkOutDate == null) {
			checkOutDate = checkInDate.plusDays(1);
		}

		return accommodation.reservations.any().checkInDate.notBetween(checkInDate, checkOutDate.minusDays(1))
			.and(accommodation.reservations.any().checkOutDate.notBetween(checkInDate.plusDays(1), checkOutDate));
	}

	private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
		return minPrice == null && maxPrice == null ? null : accommodation.price.between(minPrice, maxPrice);
	}

	private BooleanExpression numberOfGuestGoe(Integer numberOfGuest) {
		return numberOfGuest != null ? accommodation.maximumNumberOfGuest.goe(numberOfGuest) : null;
	}
}
