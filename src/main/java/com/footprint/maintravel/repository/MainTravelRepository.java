package com.footprint.maintravel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.footprint.maintravel.domain.MainTravel;

public interface MainTravelRepository extends JpaRepository<MainTravel, Long> {

	@EntityGraph(attributePaths = {"writer"})
	Optional<MainTravel> findWithWriterById(Long travelId);
}
