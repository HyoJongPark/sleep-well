package com.sleepwell.reservation.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sleepwell.reservation.domain.Reservation;
import com.sleepwell.reservation.domain.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query(
		"select count(r) > 0 from Reservation r where r.accommodation.id = :accommodationId and r.reservationStatus != :status and ((r.checkInDate <= :checkInDate and :checkInDate < r.checkOutDate) or (r.checkInDate < :checkOutDate and :checkOutDate <= r.checkOutDate))"
	)
	boolean existsReservationInAccommodationThatDay(Long accommodationId, LocalDate checkInDate,
		LocalDate checkOutDate, ReservationStatus status);
}
