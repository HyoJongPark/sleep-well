package com.sleepwell.accommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sleepwell.accommodation.domain.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
