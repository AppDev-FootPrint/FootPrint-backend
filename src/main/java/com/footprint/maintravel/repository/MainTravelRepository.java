package com.footprint.maintravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footprint.maintravel.domain.MainTravel;

public interface MainTravelRepository extends JpaRepository<MainTravel, Long> {
}
